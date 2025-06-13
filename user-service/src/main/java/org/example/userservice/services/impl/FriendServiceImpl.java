package org.example.userservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.exceptions.FriendNotFoundException;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.FriendService;
import org.example.userservice.utiles.Mapper;
import org.example.userservice.utiles.RedisForStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qwerdsa53.shared.model.entity.User;
import qwerdsa53.shared.model.notification.FriendRequestNotification;
import qwerdsa53.shared.model.type.NOTIFICATION_STATUS;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserAccessService userAccess;
    private final ObjectMapper objectMapper;
    private final RedisForStatus redis;
    private final Mapper mapper;

    @Override
    @Transactional()
    public void sendFriendRequest(Long requesterId, Long targetUserId) {
        validateRequest(requesterId, targetUserId);
        User requester = userAccess.getByIdWithLockOrThrow(requesterId);
        User targetUser = userAccess.getByIdWithLockOrThrow(targetUserId);

        ensureNotFriends(targetUser, requesterId);
        ensureNotInBlacklist(targetUser, requesterId);
        ensureNoDuplicateRequest(targetUser, requesterId);
        if (requester.getFriendRequests().contains(targetUserId)) {
            acceptMutualFriendRequest(requester, targetUser);
            return;
        }
        targetUser.getFriendRequests().add(requesterId);
        sendNotification(requesterId, targetUserId);
    }

    @Transactional(readOnly = true)
    public Page<LiteUserDto> getFriendRequests(Long userId, int page, int size) {
        User user = userAccess.getByIdOrThrow(userId);

        Set<Long> friendRequestIds = user.getFriendRequests();
        if (friendRequestIds.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("username").ascending()
        );
        return userRepository.findFriendRequestsByUserId(friendRequestIds, pageable)
                .map(requester -> {
                    boolean isOnline = redis
                            .isOnline("user:online:" + requester.getId())
                            .orElse(false);
                    return mapper.convertToLiteDto(requester, isOnline);
                });
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long userId, Long requesterId) {
        User user = userAccess.getByIdWithLockOrThrow(userId);
        User requester = userAccess.getByIdWithLockOrThrow(requesterId);

        if (!user.getFriendRequests().contains(requesterId)) {
            throw new IllegalStateException("No friend request from this user");
        }

        user.getFriendRequests().remove(requesterId);
        user.getFriends().add(requesterId);
        requester.getFriends().add(userId);
    }

    @Override
    @Transactional
    public void rejectFriendRequest(Long currentUserId, Long fromUserId) {
        User user = userAccess.getByIdWithLockOrThrow(currentUserId);
        user.getFriendRequests().remove(fromUserId);
    }

    @Override
    @Transactional
    public void deleteFriend(Long requesterId, Long targetId) {
        User requester = userAccess.getByIdWithLockOrThrow(requesterId);
        User target = userAccess.getByIdWithLockOrThrow(targetId);
        ensureIsFriends(requester, target);
        requester.getFriends().remove(targetId);
        target.getFriends().remove(requesterId);

        userRepository.save(requester);
        userRepository.save(target);
    }

    @Transactional(readOnly = true)
    public Page<LiteUserDto> getFriends(Long userId, int page, int size) {
        User user = userAccess.getByIdOrThrow(userId);

        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("username")
        );

        return userRepository.findFriendsByUserId(user.getFriends(), pageable)
                .map(friend -> {
                    boolean isOnline = redis
                            .isOnline("user:online:" + friend.getId())
                            .orElse(false);
                    return mapper.convertToLiteDto(friend, isOnline);
                });
    }

    private void validateRequest(Long requesterId, Long targetUserId) {
        if (requesterId.equals(targetUserId)) {
            throw new IllegalArgumentException("You can't add yourself to a friend list");
        }
    }

    @SneakyThrows
    private void sendNotification(Long requesterId, Long targetUserId) {
        FriendRequestNotification notification = new FriendRequestNotification(
                targetUserId,
                requesterId,
                LocalDateTime.now(),
                NOTIFICATION_STATUS.UNREAD
        );
        String payload = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(notification);
        kafkaTemplate.send("notifications", payload);
        log.info("Sent notification to WebSocket topic: {}", payload);
    }

    private void acceptMutualFriendRequest(User requester, User target) {
        requester.getFriendRequests().remove(target.getId());
        target.getFriends().add(requester.getId());
        requester.getFriends().add(target.getId());
    }

    private void ensureNotFriends(User user, Long otherId) {
        if (user.getFriends().contains(otherId)) {
            throw new IllegalStateException("User is already a friend");
        }
    }

    private void ensureIsFriends(User requester, User target) {
        if (!requester.getFriends().contains(target.getId())
                || !target.getFriends().contains(target.getId())) {
            throw new FriendNotFoundException("You are not friends with this user");
        }
    }

    private void ensureNotInBlacklist(User user, Long otherId) {
        if (user.getUserBlackList().contains(otherId)) {
            throw new IllegalStateException("You are in the blacklist of this user");
        }
    }

    private void ensureNoDuplicateRequest(User targetUser, Long requesterId) {
        if (targetUser.getFriendRequests().contains(requesterId)) {
            throw new IllegalStateException("Friend request already sent");
        }
    }
}
