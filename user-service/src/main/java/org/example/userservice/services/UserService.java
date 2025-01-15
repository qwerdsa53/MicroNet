package org.example.userservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.UserNotFoundException;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.model.dto.UserRegistrationDto;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.utiles.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRedisService userRedisService;


    @Async
    public void registerUser(UserRegistrationDto userDto) {
        log.info("Executing in thread: {}", Thread.currentThread().getName());

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role("USER")
                .birthday(userDto.getBirthday())
                .description(userDto.getDescription())
                .enabled(false)
                .build();
        try {
            userRepository.save(user);
            userRedisService.saveUser(user.getId(), convertToDto(user), 1000);
        } catch (DataIntegrityViolationException e) {
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
            UserDto userDto = userRedisService.getUser(id);
            if (userDto != null) {
                log.info("User found in cache: {}", id);
                userRedisService.updateTTL(id, 1000);
                return userDto;
            }
            userDto = userRepository.findById(id)
                    .map(this::convertToDto)
                    .orElseThrow(() -> new UserNotFoundException("user not found"));
            userRedisService.saveUser(userDto.getId(), userDto, 1000000);
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
        userRedisService.deleteUser(id);
    }

    @Transactional
    public void activateUser(long userId) {
        userRepository.enableUserById(userId);
    }

    public void updateUser(String authorizationHeader, UserDto userDto) {
        userDto.setId(getUserId(authorizationHeader));
        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!hasChanges(existingUser, userDto)) {
            return;
        }
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setDescription(userDto.getDescription());
        existingUser.setBirthday(userDto.getBirthday());
        existingUser.setEnabled(userDto.isEnabled());
        log.error("Password: {}", existingUser.getPassword());

        userRepository.save(existingUser);

        userRedisService.saveUser(userDto.getId(), userDto, 3600);
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

    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .build();
    }

}
