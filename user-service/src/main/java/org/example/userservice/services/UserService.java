package org.example.userservice.services;

import org.example.userservice.model.User;
import org.example.userservice.model.dto.UserDto;

public interface UserService {
    void registerUser(UserDto userDto);

    UserDto getUserInfo(String authorizationHeader);

    UserDto getUserById(Long id);

    void deleteUser(Long id);

    void activateUser(long userId);

    UserDto updateUser(String authorizationHeader, UserDto userDto);

    void updatePassword(Long userId, String newPassword);

    User findUserByEmail(String email);

}
