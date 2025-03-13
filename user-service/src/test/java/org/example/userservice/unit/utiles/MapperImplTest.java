package org.example.userservice.unit.utiles;


import org.example.userservice.model.Image;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.utiles.Mapper;
import org.example.userservice.utiles.MapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperImplTest {
    private User user;
    private final Mapper mapper = new MapperImpl();

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("qwerdsa53")
                .password("10101010")
                .email("qwerdsa53@gmail.com")
                .enabled(true)
                .roles(Set.of(Role.ROLE_USER))
                .friends(Set.of(2L, 3L, 4L))
                .userBlackList(Set.of(5L, 6L))
                .friendRequests(Set.of(7L, 8L))
                .createdAt(LocalDateTime.MIN)
                .updatedAt(LocalDateTime.now())
                .birthday(LocalDate.MIN)
                .description("bla bla bla")
                .build();
    }

    @Test
    void convertToDto() {
        Image image1 = Image.builder()
                .id(1L)
                .url("http...")
                .imageName("Name")
                .contentType("jpg")
                .size(1212L)
                .uploadedAt(LocalDateTime.MIN)
                .user(user)
                .build();

        Image image2 = Image.builder()
                .id(2L)
                .url("https...")
                .imageName("Name2")
                .contentType("png")
                .size(11L)
                .uploadedAt(LocalDateTime.MIN)
                .user(user)
                .build();
        user.setProfilePictures(List.of(image1, image2));

        LiteUserDto userDto = mapper.convertToDto(user);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getAvatarUrl(), image1.getUrl());
    }

    @Test
    void convertToDto_withEmptyProfilePictures() {
        LiteUserDto userDto = mapper.convertToDto(user);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getAvatarUrl(), "");
    }


    @Test
    void convertToDto_withNullProfilePictures() {
        user.setProfilePictures(null);

        LiteUserDto userDto = mapper.convertToDto(user);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getAvatarUrl(), "");
    }
}
