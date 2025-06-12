package org.example.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.services.BlackListService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/blacklist")
@RequiredArgsConstructor
public class BlackListController {
    private final BlackListService blackListService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping()
    public Page<LiteUserDto> getBlackListedUsers(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return blackListService.getBlackList(userId, page, size);
    }

    @PostMapping("/{targetUserId}")
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long targetUserId) {
        blackListService.addToBlackList(userId, targetUserId);
    }

    @DeleteMapping("/{targetUserId}")
    public void anBlockUser(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long targetUserId
    ) {
        blackListService.removeFromBlackList(userId, targetUserId);
    }
}