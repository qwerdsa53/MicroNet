package org.example.userservice.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.dto.TokenDTO;
import org.example.userservice.model.LoginRequest;
import org.example.userservice.model.User;
import org.example.userservice.services.BlacklistService;
import org.example.userservice.services.CustomAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final CustomAuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistService blacklistService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

            String token = jwtTokenProvider.generateToken(user);

            return ResponseEntity.ok(new TokenDTO(token));
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            long remainingTime = jwtTokenProvider.getRemainingTimeFromToken(token);

            if (remainingTime > 0) {
                blacklistService.addToBlacklist(token, remainingTime);
                return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token has already expired");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
    }
}