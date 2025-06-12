package org.example.userservice.controllers;


import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.model.dto.*;
import org.example.userservice.services.JwtBlackListService;
import org.example.userservice.services.UserService;
import org.example.userservice.services.impl.CustomAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import qwerdsa53.shared.model.entity.User;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("api/v1/user/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final CustomAuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtBlackListService jwtBlackListService;
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<JwtResponse> registerUser(
            @RequestPart("data") UserDto userDto,
            @RequestPart(name = "files", required = false) FilesUrlDto files
    ) throws FileUploadException {
        return userService.registerUser(userDto, files);
    }


    @PostMapping("/login")
    public TokenDto authenticateUser(@RequestBody LoginRequestDto loginRequest) {
        User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String token = jwtTokenProvider.generateToken(user);
        return new TokenDto(token);
    }

    @PostMapping("/send/confirm")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sendConfirmEmail(@RequestHeader("X-User-Id") Long userId) {
        String email = userService.getUserInfo(userId).getEmail();
        kafkaTemplate.send("mail-service", email);
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
        jwtBlackListService.addToBlacklist(token, remainingTime);
    }
}