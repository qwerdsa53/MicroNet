package org.example.userservice.services;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<JwtResponse> registerUser(UserDto userDto, List<MultipartFile> profilePictures) throws FileUploadException;

    UserDto getUserInfo(String authorizationHeader);

    UserDto getUserById(Long id);

    void deleteUser(Long id);

    void activateUser(String username);

    UserDto updateUser(String authorizationHeader, UserDto userDto, List<MultipartFile> profilePictures) throws FileUploadException;

    void updatePassword(Long userId, String newPassword);

    User findUserByEmail(String email);

}
