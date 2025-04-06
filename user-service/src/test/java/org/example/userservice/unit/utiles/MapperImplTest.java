package org.example.userservice.unit.utiles;


import org.example.userservice.model.Image;
import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;
import org.example.userservice.utiles.Mapper;
import org.example.userservice.utiles.MapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapperImplTest {
    private User temp;
    private Image imageTemp1;
    private Image imageTemp2;
    private final Mapper mapper = new MapperImpl();

    @BeforeEach
    void setUp() {
        temp = User.builder()
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
                .lastSeen(LocalDateTime.MIN)
                .build();

        imageTemp1 = Image.builder()
                .id(1L)
                .url("http...")
                .uploadedAt(LocalDateTime.MIN)
                .build();

        imageTemp2 = Image.builder()
                .id(2L)
                .url("https...")
                .uploadedAt(LocalDateTime.MIN)
                .build();
    }

    @Test
    void convertToLiteDto_success() {
        User user = temp.clone();
        Image image1 = imageTemp1.clone();
        Image image2 = imageTemp2.clone();
        user.setProfilePictures(List.of(image1, image2));
        image1.setUser(user);
        image2.setUser(user);

        LiteUserDto liteUserDto = mapper.convertToLiteDto(user, true);

        assertEquals(liteUserDto.getId(), user.getId());
        assertEquals(liteUserDto.getUsername(), user.getUsername());
        assertEquals(liteUserDto.getAvatarUrl(), image1.getUrl());
        assertEquals(liteUserDto.getLastSeen(), user.getLastSeen());
        assertTrue(liteUserDto.getIsOnline());
    }

    @Test
    void convertToLiteDto_withEmptyProfilePictures() {
        User user = temp.clone();
        user.setProfilePictures(new ArrayList<>());
        LiteUserDto liteUserDto = mapper.convertToLiteDto(user, true);

        assertEquals(liteUserDto.getId(), user.getId());
        assertEquals(liteUserDto.getUsername(), user.getUsername());
        assertEquals(liteUserDto.getAvatarUrl(), "");
        assertEquals(liteUserDto.getLastSeen(), user.getLastSeen());
        assertTrue(liteUserDto.getIsOnline());
    }


    @Test
    void convertToLiteDto_withNullProfilePictures() {
        User user = temp.clone();
        user.setProfilePictures(null);

        LiteUserDto liteUserDto = mapper.convertToLiteDto(user, true);

        assertEquals(liteUserDto.getId(), user.getId());
        assertEquals(liteUserDto.getUsername(), user.getUsername());
        assertEquals(liteUserDto.getAvatarUrl(), "");
        assertEquals(liteUserDto.getLastSeen(), user.getLastSeen());
        assertTrue(liteUserDto.getIsOnline());
    }

    @Test
    void convertToDto_success() {
        User user = temp.clone();
        Image image1 = imageTemp1.clone();
        Image image2 = imageTemp2.clone();
        user.setProfilePictures(List.of(image1, image2));
        image1.setUser(user);
        image2.setUser(user);

        UserDto userDto = mapper.convertToDto(user, true);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getBirthday(), user.getBirthday());
        assertEquals(userDto.getDescription(), user.getDescription());
        assertEquals(userDto.getProfilePictures(), user.getProfilePictures().stream().map(Image::getUrl).toList());
        assertEquals(userDto.getLastSeen(), user.getLastSeen());
        assertTrue(userDto.getIsOnline());
    }

    @Test
    void convertToDto_withEmptyProfilePictures() {
        User user = temp.clone();
        user.setProfilePictures(new ArrayList<>());

        UserDto userDto = mapper.convertToDto(user, true);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getBirthday(), user.getBirthday());
        assertEquals(userDto.getDescription(), user.getDescription());
        assertEquals(userDto.getProfilePictures(), Collections.emptyList());
        assertEquals(userDto.getLastSeen(), user.getLastSeen());
        assertTrue(userDto.getIsOnline());
    }

    @Test
    void convertToDto_withNullProfilePictures() {
        User user = temp.clone();
        user.setProfilePictures(null);

        UserDto userDto = mapper.convertToDto(user, true);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getBirthday(), user.getBirthday());
        assertEquals(userDto.getDescription(), user.getDescription());
        assertEquals(userDto.getProfilePictures(), Collections.emptyList());
        assertEquals(userDto.getLastSeen(), user.getLastSeen());
        assertTrue(userDto.getIsOnline());
    }
}
