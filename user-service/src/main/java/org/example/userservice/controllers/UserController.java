package org.example.userservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
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
    public UserDto getCurrentUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        return userService.getUserInfo(authorizationHeader);
    }

    @GetMapping("/{id}")
    public UserDto getUserInfo(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDto updateUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestPart(name = "files", required = false) List<MultipartFile> profilePictures,
            @RequestPart(name = "data") UserDto userDto
    ) throws FileUploadException{
        return userService.updateUser(authorizationHeader, userDto, profilePictures);
    }

}
