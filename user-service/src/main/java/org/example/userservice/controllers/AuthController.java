package org.example.userservice.controllers;


import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.LoginRequestDto;
import org.example.userservice.model.dto.TokenDto;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.services.BlackListService;
import org.example.userservice.services.UserService;
import org.example.userservice.services.impl.CustomAuthService;
import org.example.userservice.services.impl.MailServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/user/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final CustomAuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlackListService blacklistService;
    private final UserService userService;
    private final MailServiceClient mailServiceClient;


    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public JwtResponse registerUser(
            @RequestPart(name = "files", required = false) List<MultipartFile> profilePictures,
            @RequestPart(name = "data") UserDto userDto
    ) throws FileUploadException {
        return userService.registerUser(userDto, profilePictures);
    }


    @PostMapping("/login")
    public TokenDto authenticateUser(@RequestBody LoginRequestDto loginRequest) {
        User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String token = jwtTokenProvider.generateToken(user);
        return new TokenDto(token);
    }

    @PostMapping("/send/confirm")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sendConfirmEmail(@RequestHeader("Authorization") String authorizationHeader) {
        String email =  userService.getUserInfo(authorizationHeader).getEmail();
        mailServiceClient.sendEmail(email);
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