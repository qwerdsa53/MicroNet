package org.example.userservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.exceptions.FriendNotFoundException;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.model.NOTIFICATION_STATUS;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.notifications.FriendRequestNotification;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.FriendService;
import org.example.userservice.utiles.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Mapper mapper;

    @SneakyThrows
    @Override
    @Transactional()
    public void sendFriendRequest(Long requesterId, Long targetUserId) {
        if (requesterId.equals(targetUserId)) {
            throw new IllegalArgumentException("You can't add yourself to a friend list");
        }
        User requester = userRepository.findUserWithLock(requesterId)
                .orElseThrow(() -> new UserNotFoundException("Requester not found"));
        User targetUser = userRepository.findUserWithLock(targetUserId)
                .orElseThrow(() -> new UserNotFoundException("Target user not found"));

        if (targetUser.getFriends().contains(requesterId)) {
            throw new IllegalStateException("User is already a friend");
        }
        if (targetUser.getFriendRequests().contains(requesterId)) {
            throw new IllegalStateException("Friend request already sent");
        }
        if (requester.getFriendRequests().contains(targetUserId)) {
            requester.getFriendRequests().remove(targetUserId);
            targetUser.getFriends().add(requesterId);
            requester.getFriends().add(targetUserId);
            return;
        }
        FriendRequestNotification notification = new FriendRequestNotification(
                targetUserId,
                requesterId,
                LocalDateTime.now(),
                NOTIFICATION_STATUS.UNREAD
        );
        targetUser.getFriendRequests().add(requesterId);
        String payload = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(notification);
        kafkaTemplate.send("notifications", payload);
        log.info("Sent notification to WebSocket topic: {}", payload);
    }

    @Transactional(readOnly = true)
    public Page<LiteUserDto> getFriendRequests(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<Long> friendRequestIds = user.getFriendRequests();
        if (friendRequestIds.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return userRepository.findFriendRequestsByUserId(friendRequestIds, pageable)
                .map(mapper::convertToLiteDto);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long userId, Long requesterId) {
        User user = userRepository.findUserWithLock(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        User requester = userRepository.findUserWithLock(requesterId)
                .orElseThrow(() -> new UserNotFoundException("Requester not found"));

        if (!user.getFriendRequests().contains(requesterId)) {
            throw new IllegalStateException("No friend request from this user");
        }

        user.getFriendRequests().remove(requesterId);
        user.getFriends().add(requesterId);
        requester.getFriends().add(userId);
    }

    @Override
    @Transactional
    public void rejectFriendRequest(Long requesterId, Long target) {
        User user = userRepository.findById(requesterId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getFriendRequests().remove(target);
    }

    @Override
    @Transactional
    public void deleteFriend(Long requesterId, Long targetId) {
        User requester = userRepository.findUserWithLock(requesterId)
                .orElseThrow(() -> new UserNotFoundException("Requester not found"));
        User target = userRepository.findUserWithLock(targetId)
                .orElseThrow(() -> new UserNotFoundException("Target user not found"));

        if (!requester.getFriends().contains(targetId) || !target.getFriends().contains(requesterId)) {
            throw new FriendNotFoundException("You are not friends with this user");
        }

        requester.getFriends().remove(targetId);
        target.getFriends().remove(requesterId);

        userRepository.save(requester);
        userRepository.save(target);
    }

    @Transactional(readOnly = true)
    public Page<LiteUserDto> getFriends(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<Long> friends = user.getFriends();
        if (friends.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("username"));

        return userRepository.findFriendsByUserId(user.getFriends(), pageable)
                .map(mapper::convertToLiteDto);
    }
}
