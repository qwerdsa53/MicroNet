package org.example.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.userservice.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping()
    public Page<LiteUserDto> getFriends(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = jwtTokenProvider.getUserIdFromToken(authorizationHeader);
        return friendService.getFriends(userId, page, size);
    }

    @PostMapping("/request/{targetUserId}")
    @ResponseStatus(HttpStatus.OK)
    public void sendFriendRequest(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long targetUserId) {
        Long userId = jwtTokenProvider.getUserIdFromToken(authorizationHeader);
        friendService.sendFriendRequest(userId, targetUserId);
    }

    @GetMapping("/requests")
    public Page<LiteUserDto> getFriendRequests(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = jwtTokenProvider.getUserIdFromToken(authorizationHeader);
        return friendService.getFriendRequests(userId, page, size);
    }

    @PostMapping("/accept/{requesterId}")
    public void acceptFriendRequest(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long requesterId) {
        Long userId = jwtTokenProvider.getUserIdFromToken(authorizationHeader);
        friendService.acceptFriendRequest(userId, requesterId);
    }

    @DeleteMapping("/reject/{target}")
    public void rejectFriendRequest(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long target) {
        Long requesterId = jwtTokenProvider.getUserIdFromToken(authorizationHeader);
        friendService.rejectFriendRequest(requesterId, target);
    }

    @DeleteMapping("/{requesterId}")
    public void deleteFriend(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long requesterId) {
        Long userId = jwtTokenProvider.getUserIdFromToken(authorizationHeader);
        friendService.deleteFriend(userId, requesterId);
    }
}