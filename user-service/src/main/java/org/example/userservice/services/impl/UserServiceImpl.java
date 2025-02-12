package org.example.userservice.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.UserService;
import org.example.userservice.utiles.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Async
    @Transactional
    public void registerUser(UserDto userDto) {
        log.info("Executing in thread: {}", Thread.currentThread().getName());

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getRawPassword()))
                .email(userDto.getEmail())
                .roles(Set.of(Role.ROLE_USER))
                .birthday(userDto.getBirthday())
                .description(userDto.getDescription())
                .enabled(false)
                .build();

        log.error("User: {}", user);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Username already exists");
        } catch (Exception e) {
            throw new RuntimeException("Error during registration process: " + e.getMessage(), e);
        }
    }

    public UserDto getUserInfo(String authorizationHeader) {
        Long id = getUserId(authorizationHeader);
        return getUserById(id);
    }

    public UserDto getUserById(Long id) {
        try {
            UserDto userDto = userRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new UserNotFoundException("user not found"));
            log.info("User fetched from database: {}", id);
            return userDto;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("user not found");
        } catch (Exception e) {
            log.error("Exception message: {}", e.getMessage());
        }
        return null;
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void activateUser(long userId) {
        userRepository.enableUserById(userId);
    }

    public UserDto updateUser(String authorizationHeader, UserDto userDto) {
        userDto.setId(getUserId(authorizationHeader));
        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!hasChanges(existingUser, userDto)) {
            return null;
        }
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setDescription(userDto.getDescription());
        existingUser.setBirthday(userDto.getBirthday());
        existingUser.setEnabled(userDto.isEnabled());

        userRepository.save(existingUser);

        return userDto;
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private boolean hasChanges(User existingUser, UserDto userDto) {
        return !existingUser.getUsername().equals(userDto.getUsername()) ||
                !existingUser.getEmail().equals(userDto.getEmail()) ||
                !existingUser.getDescription().equals(userDto.getDescription()) ||
                !existingUser.getBirthday().equals(userDto.getBirthday()) ||
                existingUser.isEnabled() != userDto.isEnabled();
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
    }

    private Long getUserId(String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        return jwtUtil.extractUserId(token);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .build();
    }

}
