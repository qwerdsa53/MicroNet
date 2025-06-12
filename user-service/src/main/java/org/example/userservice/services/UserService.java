package org.example.userservice.services;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.model.dto.FilesUrlDto;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<JwtResponse> registerUser(UserDto userDto, FilesUrlDto profilePictures) throws FileUploadException;

    UserDto getUserInfo(Long userId);

    UserDto getUserById(Long requesterId, Long id);

    LiteUserDto getLiteUserById(Long id);

    void deleteUser(Long id);

    void activateUser(String username);

    UserDto updateUser(Long userId, UserDto userDto, List<String> profilePictures) throws FileUploadException;

    void updatePassword(Long userId, String newPassword);

}
