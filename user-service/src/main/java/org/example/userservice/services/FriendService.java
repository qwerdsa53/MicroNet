package org.example.userservice.services;

import org.example.userservice.model.dto.LiteUserDto;
import org.springframework.data.domain.Page;

public interface FriendService {
    void sendFriendRequest(Long requestId, Long targetUserId);

    Page<LiteUserDto> getFriendRequests(Long userId, int page, int size);

    Page<LiteUserDto> getFriends(Long userId, int page, int size);

    void acceptFriendRequest(Long userId, Long requesterId);

    void rejectFriendRequest(Long requesterId, Long targetId);

    void deleteFriend(Long requesterId, Long targetId);
}
