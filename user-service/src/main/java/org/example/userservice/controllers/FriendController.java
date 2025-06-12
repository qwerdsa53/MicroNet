package org.example.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.services.FriendService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping()
    public Page<LiteUserDto> getFriends(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return friendService.getFriends(userId, page, size);
    }

    @PostMapping("/request/{targetUserId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendFriendRequest(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long targetUserId) {
        friendService.sendFriendRequest(userId, targetUserId);
    }

    @GetMapping("/requests")
    public Page<LiteUserDto> getFriendRequests(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return friendService.getFriendRequests(userId, page, size);
    }

    @PostMapping("/accept/{requesterId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void acceptFriendRequest(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long requesterId) {
        friendService.acceptFriendRequest(userId, requesterId);
    }

    @DeleteMapping("/reject/{target}")
    public void rejectFriendRequest(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long target) {
        friendService.rejectFriendRequest(userId, target);
    }

    @DeleteMapping("/{targetId}")
    public void deleteFriend(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long targetId) {
        friendService.deleteFriend(userId, targetId);
    }
}