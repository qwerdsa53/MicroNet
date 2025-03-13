package org.example.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.services.BlackListService;
import org.example.userservice.utiles.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/blacklist")
@RequiredArgsConstructor
public class BlackListController {
    private final BlackListService blackListService;
    private final JwtUtil jwtUtil;

    @GetMapping()
    public Page<LiteUserDto> getBlackListedUsers(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        return blackListService.getBlackList(userId, page, size);
    }

    @PostMapping("/{targetUserId}")
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long targetUserId) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        blackListService.addToBlackList(userId, targetUserId);
    }

    @DeleteMapping("/{targetUserId}")
    public void anBlockUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long targetUserId
    ) {
        Long userId = jwtUtil.extractUserId(authorizationHeader);
        blackListService.removeFromBlackList(userId, targetUserId);
    }
}