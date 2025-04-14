package org.example.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.BlackListService;
import org.example.userservice.services.FriendService;
import org.example.userservice.utiles.Mapper;
import org.example.userservice.utiles.RedisForStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qwerdsa53.shared.model.entity.User;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BlackListServiceImpl implements BlackListService {
    private final UserRepository userRepository;
    private final FriendService friendService;
    private final RedisForStatus redis;
    private final Mapper mapper;

    @Override
    @Transactional
    public void addToBlackList(Long requesterId, Long targetId) {
        User requester = userRepository.findUserWithLock(requesterId)
                .orElseThrow(() -> new UserNotFoundException("Requester not found"));

        if (!userRepository.existsById(targetId)) {
            throw new UserNotFoundException("Target user not found");
        }
        if (requester.getUserBlackList().contains(targetId)) {
            throw new IllegalArgumentException("User already blacklisted");
        }

        if (requester.getFriends().contains(targetId)) {
            friendService.deleteFriend(requesterId, targetId);
        }
        requester.getUserBlackList().add(targetId);
        userRepository.save(requester);
    }

    @Override
    @Transactional
    public void removeFromBlackList(Long requesterId, Long targetId) {
        User requester = userRepository.findUserWithLock(requesterId)
                .orElseThrow(() -> new UserNotFoundException("Requester not found"));

        if (!userRepository.existsById(targetId)) {
            throw new UserNotFoundException("Target user not found");
        }
        if (!requester.getUserBlackList().contains(targetId)) {
            throw new IllegalArgumentException("User not in blacklist");
        }

        requester.getUserBlackList().remove(targetId);
        userRepository.save(requester);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LiteUserDto> getBlackList(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<Long> blackListedIds = user.getUserBlackList();
        if (blackListedIds.isEmpty()) {
            return Page.empty(PageRequest.of(page, size));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return userRepository.findBlackListedUsers(blackListedIds, pageable)
                .map(blackListedUser -> {
                    boolean isOnline = redis.isOnline("user:online:" + blackListedUser.getId()).orElse(false);
                    return mapper.convertToLiteDto(blackListedUser, isOnline);
                });
    }
}
