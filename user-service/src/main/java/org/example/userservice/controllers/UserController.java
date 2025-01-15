package org.example.userservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.UserNotFoundException;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.services.UserService;
import org.example.userservice.utiles.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(authorizationHeader));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some error");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserDto userDTO) {
        try {
            userService.updateUser(authorizationHeader, userDTO);
            return ResponseEntity.status(HttpStatus.OK).body("User updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating User: " + e.getMessage());
        }
    }


}
