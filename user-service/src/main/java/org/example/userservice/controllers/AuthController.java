package org.example.userservice.controllers;


import jakarta.websocket.server.PathParam;
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
    @ResponseStatus(value = HttpStatus.CREATED)
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

    @PostMapping("/confirm")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void confirmUser(@PathParam("token") String token) {
        userService.activateUser(token);
    }

    @PostMapping("/logout")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        long remainingTime = jwtTokenProvider.getRemainingTimeFromToken(token);
        blacklistService.addToBlacklist(token, remainingTime);
    }
}