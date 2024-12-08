package org.example.userservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.userservice.dto.UserRegistrationDto;
import org.example.userservice.model.User;
import org.example.userservice.repo.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Async
    public void registerUser(UserRegistrationDto userDto) {
        System.out.println("Executing in thread: " + Thread.currentThread().getName());
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role("USER")
                .enabled(false)
                .build();
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists");
        } catch (Exception e) {
            throw new RuntimeException("Error during registration process: " + e.getMessage(), e);
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void activateUser(long userId) {
        userRepository.enableUserById(userId);
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

}
