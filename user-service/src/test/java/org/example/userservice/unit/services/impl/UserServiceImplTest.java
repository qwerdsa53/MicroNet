package org.example.userservice.unit.services.impl;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.exceptions.UserAlreadyExistException;
import org.example.userservice.model.dto.FilesUrlDto;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.repo.ImageRepo;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.impl.MailServiceClient;
import org.example.userservice.services.impl.TokenServiceImpl;
import org.example.userservice.services.impl.UserServiceImpl;
import org.example.userservice.utiles.Mapper;
import org.example.userservice.utiles.RedisForStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import qwerdsa53.shared.model.entity.Image;
import qwerdsa53.shared.model.entity.User;
import qwerdsa53.shared.model.type.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private TokenServiceImpl tokenService;

    @Mock
    private MailServiceClient mailServiceClient;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ImageRepo imageRepo;

    @Mock
    private Mapper mapper;


    @Mock
    private RedisForStatus redis;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void registerUser_success() throws FileUploadException, ExecutionException, InterruptedException {
        UserDto dto = UserDto.builder()
                .id(1L)
                .username("user-test")
                .email("user@gmail.com")
                .birthday(LocalDate.parse("2006-10-12"))
                .description("test description")
                .rawPassword("psswd")
                .build();

        MockMultipartFile file1 = new MockMultipartFile(
                "file1-test",
                "file1-origName",
                "image/png",
                new byte[]{}
        );
        MockMultipartFile file2 = new MockMultipartFile(
                "file2-test",
                "file2-origName",
                "image/png",
                new byte[]{}
        );
        FilesUrlDto pictures = new FilesUrlDto(List.of(
                "http://localhost:9000/pictures/5/7941dd98-2304-3590-980d-c990fd85ac2b.png",
                "http://localhost:9000/pictures/5/7941dd98-2304-1231-980d-c990fd85ac3b.png"
        ));

        User mockUser = User.builder()
                .id(1L)
                .username(dto.getUsername())
                .password("hashed-password")
                .email(dto.getEmail())
                .profilePictures(new ArrayList<>())
                .roles(Set.of(Role.ROLE_USER))
                .birthday(dto.getBirthday())
                .description(dto.getDescription())
                .enabled(false)
                .build();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(dto.getRawPassword()))
                .thenReturn("hashed-password");
        when(jwtTokenProvider.generateToken(any(User.class)))
                .thenReturn("fake-jwt-token");
        when(userRepository.save(any()))
                .thenReturn(mockUser);

        CompletableFuture<JwtResponse> response = userService.registerUser(dto, pictures);

        assertEquals(1L, response.get().getUserId());
        assertEquals("user-test", response.get().getUsername());
        assertNotNull(response.get().getAccessToken());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_userAlreadyExists() throws FileUploadException {
        UserDto dto = UserDto.builder()
                .username("user-test")
                .email("user@gmail.com")
                .rawPassword("psswd")
                .build();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(dto, new FilesUrlDto(List.of())));

        verify(userRepository, never()).save(any(User.class));

    }


    @Test
    void registerUser_verifiesServiceCalls() throws FileUploadException, ExecutionException, InterruptedException {
        UserDto dto = UserDto.builder()
                .username("user-test")
                .email("user@gmail.com")
                .rawPassword("psswd")
                .build();

        MockMultipartFile file1 = new MockMultipartFile(
                "file1-test",
                "file1-origName",
                "image/png",
                new byte[]{}
        );
        User mockUser = User.builder()
                .id(1L)
                .username(dto.getUsername())
                .password("hashed-password")
                .email(dto.getEmail())
                .profilePictures(new ArrayList<>())
                .roles(Set.of(Role.ROLE_USER))
                .birthday(dto.getBirthday())
                .description(dto.getDescription())
                .enabled(false)
                .build();

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);
        when(userRepository.save(any())).thenReturn(mockUser);
        when(passwordEncoder.encode(dto.getRawPassword()))
                .thenReturn("hashed-password");
        when(jwtTokenProvider.generateToken(any(User.class)))
                .thenReturn("fake-jwt-token");

        CompletableFuture<JwtResponse> response = userService.registerUser(dto, new FilesUrlDto(List.of("http://localhost:9000/pictures/5/7941dd98-2304-3590-980d-c990fd85ac2b.png")));
        response.get();

        verify(userRepository, times(1))
                .save(any(User.class));
        verify(jwtTokenProvider, times(1))
                .generateToken(any(User.class));
    }

    @Test
    void getUserInfo_success() {
        String authorizationHeader = "Bearer token";
        Long userId = 1L;
        Boolean isOline = true;
        UserDto expectedUser = UserDto.builder()
                .id(1L)
                .username("John Doe")
                .email("john@example.com")
                .lastSeen(LocalDateTime.MIN)
                .isOnline(true)
                .build();

        when(jwtTokenProvider.getUserIdFromToken(anyString())).thenReturn(1L);
        when(userRepository.findById(1L))
                .thenReturn(
                        Optional.of(
                                User.builder()
                                        .id(1L)
                                        .username("John Doe")
                                        .email("john@example.com")
                                        .profilePictures(List.of(Image.builder().url("https...").build()))
                                        .lastSeen(LocalDateTime.MIN)
                                        .userBlackList(new HashSet<>())
                                        .build()
                        )
                );
        when(redis.isOnline("user:online:" + userId)).thenReturn(Optional.of(isOline));
        when(mapper.convertToDto(any(), eq(isOline))).thenReturn(expectedUser);

        UserDto result = userService.getUserInfo(authorizationHeader);
        assertEquals(expectedUser, result);

        verify(userRepository, times(1)).findById(userId);
        verify(mapper, times(1)).convertToDto(any(), anyBoolean());
    }

    @Test
    void getUserById_success() {
        Long userId = 1L;
        Boolean isOline = true;
        String authorizationHeader = "Bearer token";
        User user = User.builder()
                .id(1L)
                .username("John Doe")
                .email("john@example.com")
                .profilePictures(List.of(Image.builder().url("https...").build()))
                .userBlackList(new HashSet<>())
                .build();
        UserDto expectedUser = UserDto.builder()
                .id(1L)
                .username("John Doe")
                .email("john@example.com")
                .isOnline(true)
                .build();

        when(jwtTokenProvider.getUserIdFromToken(anyString())).thenReturn(2L);
        when(redis.isOnline("user:online:" + userId)).thenReturn(Optional.of(isOline));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.convertToDto(user, true)).thenReturn(expectedUser);

        UserDto result = userService.getUserById(authorizationHeader, userId);

        assertEquals(expectedUser, result);

        verify(userRepository, times(1)).findById(userId);
        verify(mapper, times(1)).convertToDto(eq(user), anyBoolean());
    }
}
