package org.example.userservice.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.exceptions.UserAlreadyExistException;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.model.Image;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.FriendDto;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.repo.ImageRepo;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.ImageService;
import org.example.userservice.services.UserService;
import org.example.userservice.utiles.JwtUtil;
import org.hibernate.exception.JDBCConnectionException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenServiceImpl tokenService;
    private final ImageService imageService;
    private final MailServiceClient mailServiceClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final ImageRepo imageRepo;
    private final JwtUtil jwtUtil;


    @Async
    @Transactional
    @SneakyThrows
    public JwtResponse registerUser(
            @NotNull UserDto userDto,
            List<MultipartFile> profilePictures
    ) throws FileUploadException {
        log.info("Executing in thread: {}", Thread.currentThread().getName());

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getRawPassword()))
                .email(userDto.getEmail())
                .profilePictures(new ArrayList<>())
                .roles(Set.of(Role.ROLE_USER))
                .birthday(userDto.getBirthday())
                .description(userDto.getDescription())
                .enabled(false)
                .build();


        try {
            User savedUser = userRepository.save(user);
            log.info("User: {} registered", user.getUsername());
            addProfilePictures(profilePictures, savedUser);

            String token = jwtTokenProvider.generateToken(user);
            sendWelcomeEmailAsync(savedUser.getEmail());
            return new JwtResponse(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    convertToDto(savedUser.getProfilePictures()),
                    token
            );

        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                log.error("Error while registering new user {}. Email already exists. Exception message: {}",
                        userDto.getUsername(), e.getMessage(), e);
                throw new UserAlreadyExistException(String.format("User %s already exists", userDto.getUsername()));
            }
            log.error("Data integrity error while saving user {}: {}", user.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Database constraint violation", e);
        } catch (JDBCConnectionException e) {
            log.error("Database connection lost while saving user {}: {}", user.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Database connection error", e);
        } catch (Exception e) {
            log.error("Unexpected error while saving user {}: {}", user.getUsername(), e.getMessage(), e);
            throw new RuntimeException("Unexpected error", e);
        }
    }

    public UserDto getUserInfo(String authorizationHeader) {
        Long id = extractUserId(authorizationHeader);
        return getUserById(id);
    }

    public UserDto getUserById(Long id) {
        UserDto userDto = userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        log.info("User fetched from database: {}", id);
        return userDto;
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void activateUser(String token) {
        String email = tokenService.getUserEmailByToken(token);
        if(userRepository.enableUserByEmail(email) == 0){
            throw new UserNotFoundException("User not found");
        }
        tokenService.deleteToken(token);
    }

    public UserDto updateUser(
            String authorizationHeader,
            UserDto userDto,
            List<MultipartFile> profilePictures
    ) throws FileUploadException {
        userDto.setId(extractUserId(authorizationHeader));
        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setDescription(userDto.getDescription());
        existingUser.setBirthday(userDto.getBirthday());
        existingUser.setEnabled(userDto.isEnabled());
        imageRepo.deleteAll(existingUser.getProfilePictures());
        existingUser.getProfilePictures().clear();
        imageService.deleteFolder(existingUser.getId().toString());

        addProfilePictures(profilePictures, existingUser);
        userRepository.save(existingUser);
        return convertToDto(existingUser);
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional()
    public Page<FriendDto> getFriends(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("username"));

        return userRepository.findFriendsByUserId(user.getFriends(), pageable)
                .map(friend -> new FriendDto(friend.getId(), friend.getUsername(), "friend.getAvatarUrl()"));
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

    private Long extractUserId(String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        return jwtUtil.extractUserId(token);
    }

    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .profilePictures(user.getProfilePictures().stream().map(Image::getUrl).toList())
                .build();
    }

    public List<String> convertToDto(List<Image> images){
        return images.stream().map(Image::getUrl).toList();
    }

    private void sendWelcomeEmailAsync(String email) {
        CompletableFuture.runAsync(() -> {
            try {
                mailServiceClient.sendEmail(email);
            } catch (Exception e) {
                log.error("Ошибка отправки email: {}", e.getMessage());
            }
        });
    }

    private void addProfilePictures(
            List<MultipartFile> profilePictures,
            User user
    ) throws FileUploadException {
        if (profilePictures != null && !profilePictures.isEmpty()) {
            for (MultipartFile picture : profilePictures) {
                String url = imageService.upload(picture, user.getId());
                user.getProfilePictures().add(new Image(
                        url,
                        picture.getOriginalFilename(),
                        picture.getContentType(),
                        picture.getSize(),
                        LocalDateTime.now(),
                        user
                ));
            }
        }
    }
}
