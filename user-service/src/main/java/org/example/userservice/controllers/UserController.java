package org.example.userservice.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.model.dto.FilesUrlDto;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    /**
     * retrieves user information based on the provided jwt token in the authorization header.
     *
     * @param userId custom header with requester id
     * @return ResponseEntity:
     * - status 200 with user information as a json object
     * - status 400 with an error message in case of invalid input or other issues
     */
    @GetMapping
    public UserDto getCurrentUserInfo(@RequestHeader("X-User-Id") Long userId) {
        return userService.getUserInfo(userId);
    }

    @GetMapping("/{id}")
    public UserDto getUserInfo(@RequestHeader(value = "X-User-Id", required = false) Long userId, @PathVariable("id") Long id) {
        return userService.getUserById(userId, id);
    }

    @GetMapping("/lite/{id}")
    public LiteUserDto getLiteUserInfo(@PathVariable("id") Long id) {
        return userService.getLiteUserById(id);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDto updateUser(
            @RequestHeader("X-User-Id") Long userId,
            @RequestPart(name = "files", required = false) FilesUrlDto profilePictures,
            @RequestPart(name = "data") UserDto userDto
    ) throws FileUploadException {
        return userService.updateUser(userId, userDto, profilePictures.getFiles());
    }

}
