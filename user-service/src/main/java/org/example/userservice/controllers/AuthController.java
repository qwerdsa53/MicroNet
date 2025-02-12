package org.example.userservice.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.LoginRequestDto;
import org.example.userservice.model.dto.TokenDto;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.services.BlackListService;
import org.example.userservice.services.UserService;
import org.example.userservice.services.impl.CustomAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final CustomAuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlackListService blacklistService;
    private final UserService userService;


    @PostMapping("/register")
    public JwtResponse registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        User user = authService.authenticate(userDto.getUsername(), userDto.getRawPassword());
        String token = jwtTokenProvider.generateToken(user);
        return new JwtResponse(user.getId(), user.getUsername(), token);
    }


    @PostMapping("/login")
    public TokenDto authenticateUser(@RequestBody LoginRequestDto loginRequest) {
        User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String token = jwtTokenProvider.generateToken(user);
        return new TokenDto(token);
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