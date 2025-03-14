package org.example.userservice.unit.services.impl;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.example.userservice.JwtTokenProvider;
import org.example.userservice.exceptions.UserAlreadyExistException;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.JwtResponse;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.repo.ImageRepo;
import org.example.userservice.repo.UserRepository;
import org.example.userservice.services.ImageService;
import org.example.userservice.services.impl.MailServiceClient;
import org.example.userservice.services.impl.TokenServiceImpl;
import org.example.userservice.services.impl.UserServiceImpl;
import org.example.userservice.utiles.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private TokenServiceImpl tokenService;

    @Mock
    private ImageService imageService;

    @Mock
    private MailServiceClient mailServiceClient;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ImageRepo imageRepo;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        List<MultipartFile> pictures = List.of(file1, file2);

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
        when(imageService.upload(any(MultipartFile.class), anyLong()))
                .thenReturn("http://fake-url.com/image.png");

        CompletableFuture<JwtResponse> response = userService.registerUser(dto, pictures);

        assertEquals(1L, response.get().getUserId());
        assertEquals("user-test", response.get().getUsername());
        assertNotNull(response.get().getAccessToken());

        verify(userRepository, times(1)).save(any(User.class));
        verify(imageService, times(2))
                .upload(any(MultipartFile.class), anyLong());
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

        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(dto, List.of()));

        verify(userRepository, never()).save(any(User.class));
        verify(imageService, never())
                .upload(any(MultipartFile.class), anyLong());
    }

    @Test
    void registerUser_fileUploadError() throws FileUploadException {
        UserDto dto = UserDto.builder()
                .username("user-test")
                .email("user@gmail.com")
                .rawPassword("psswd")
                .build();

        MockMultipartFile file = new MockMultipartFile(
                "file1-test",
                "file1-origName",
                "image/png",
                new byte[]{}
        );
        List<MultipartFile> pictures = List.of(file);

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
        when(imageService.upload(any(MultipartFile.class), anyLong()))
                .thenThrow(new FileUploadException("Upload failed"));

        assertThrows(FileUploadException.class, () -> userService.registerUser(dto, pictures));

        verify(userRepository, times(1))
                .save(any(User.class));
        verify(imageService, times(1))
                .upload(any(MultipartFile.class), anyLong());
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
        when(imageService.upload(any(MultipartFile.class), anyLong()))
                .thenReturn("fake-url");
        when(jwtTokenProvider.generateToken(any(User.class)))
                .thenReturn("fake-jwt-token");

        CompletableFuture<JwtResponse> response = userService.registerUser(dto, List.of(file1));
        response.get();

        verify(userRepository, times(1))
                .save(any(User.class));
        verify(imageService, times(1))
                .upload(any(MultipartFile.class), anyLong());
        verify(jwtTokenProvider, times(1))
                .generateToken(any(User.class));
    }
}
