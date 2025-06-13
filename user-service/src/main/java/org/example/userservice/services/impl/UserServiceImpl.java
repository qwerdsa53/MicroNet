package org.example.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.exceptions.UserAlreadyExistException;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.model.dto.FilesUrlDto;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.repo.ImageRepo;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.UserService;
import org.example.userservice.utiles.RedisForStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qwerdsa53.shared.model.entity.Image;
import qwerdsa53.shared.model.entity.User;
import qwerdsa53.shared.model.type.Role;

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
    private final MailServiceClient mailServiceClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisForStatus redis;
    private final UserAccessService userAccess;
    private final ImageRepo imageRepo;
    private final UserMapper userMapper;

    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<JwtResponse> registerUser(
            @NotNull UserDto userDto,
            FilesUrlDto profilePictures
    ) {
        log.info("Executing in thread: {}", Thread.currentThread().getName());

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException(String.format("User %s already exists", userDto.getEmail()));
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getRawPassword()))
                .email(userDto.getEmail())
                .profilePictures(new ArrayList<>())
                .roles(Set.of(Role.ROLE_USER))
                .birthday(userDto.getBirthday())
                .description(userDto.getDescription())
                .lastSeen(LocalDateTime.now())
                .enabled(false)
                .build();


        User savedUser = userRepository.save(user);
        log.info("User: {} registered", user.getUsername());
        addProfilePictures(profilePictures.getFiles(), savedUser);

        String token = jwtTokenProvider.generateToken(user);
        mailServiceClient.sendWelcomeEmail(savedUser.getEmail());
        return CompletableFuture.completedFuture(new JwtResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                convertToDto(savedUser.getProfilePictures()),
                token
        ));
    }

    @Transactional(readOnly = true)
    public UserDto getUserInfo(Long userId) {
        return getUserById(userId, userId);
    }

    public UserDto getUserById(Long requesterId, Long id) {
        User user = userAccess.getByIdOrThrow(id);
        Boolean isOnline = redis
                .isOnline("user:online:" + id)
                .orElse(false);
        if (user.getUserBlackList().contains(requesterId)) {
            user.setBirthday(null);
            user.setDescription(null);
            user.setLastSeen(null);
            user.setProfilePictures(null);
            isOnline = null;
        }
        return userMapper.convertToDto(user, isOnline);
    }

    @Override
    public LiteUserDto getLiteUserById(Long id) {
        User user = userAccess.getByIdOrThrow(id);
        Boolean isOnline = redis
                .isOnline("user:online:" + id)
                .orElse(false);
        return userMapper.convertToLiteDto(user, isOnline);
    }


    @Transactional
    public void deleteUser(Long id) {
        User user = userAccess.getByIdWithLockOrThrow(id);
        userRepository.delete(user);
    }

    @Transactional
    public synchronized void activateUser(String token) {
        String email = tokenService.getUserEmailByToken(token);
        if (userRepository.enableUserByEmail(email) == 0) {
            throw new UserNotFoundException("User not found");
        }
        tokenService.deleteToken(token);
    }

    @Transactional
    public UserDto updateUser(
            Long userId,
            UserDto userDto,
            List<String> profilePictures
    ) {
        userDto.setId(userId);
        User existingUser = userAccess.getByIdOrThrow(userId);

        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setDescription(userDto.getDescription());
        existingUser.setBirthday(userDto.getBirthday());
        existingUser.setEnabled(userDto.isEnabled());
        imageRepo.deleteAll(existingUser.getProfilePictures());
        existingUser.getProfilePictures().clear();

        addProfilePictures(profilePictures, existingUser);
        userRepository.save(existingUser);

        boolean isOnline = redis.isOnline("user:online:" + existingUser.getId()).orElse(false);
        return userMapper.convertToDto(existingUser, isOnline);
    }

    public void updatePassword(Long userId, String newPassword) {
        User user = userAccess.getByIdOrThrow(userId);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    private List<String> convertToDto(List<Image> images) {
        return images.stream().map(Image::getUrl).toList();
    }

    private void addProfilePictures(
            List<String> profilePictures,
            User user
    ) {
        if (profilePictures != null && !profilePictures.isEmpty()) {
            for (String url : profilePictures) {
                user.getProfilePictures().add(new Image(
                        url,
                        LocalDateTime.now(),
                        user
                ));
            }
        }
    }
}
