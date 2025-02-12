package org.example.userservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.services.UserService;
import org.example.userservice.utiles.JwtUtil;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;


    /**
     * retrieves user information based on the provided jwt token in the authorization header.
     *
     * @param authorizationHeader the authorization header containing the jwt token
     * @return ResponseEntity:
     * - status 200 with user information as a json object
     * - status 400 with an error message in case of invalid input or other issues
     */
    @GetMapping
    public UserDto getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        return userService.getUserInfo(authorizationHeader);
    }

    @PutMapping
    public UserDto updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserDto userDTO) {
        return userService.updateUser(authorizationHeader, userDTO);
    }

}
