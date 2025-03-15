package org.example.userservice.utiles;

import org.example.userservice.model.Image;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MapperImpl implements Mapper {

    @Override
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .birthday(user.getBirthday())
                .description(user.getDescription())
                .profilePictures(
                        user.getProfilePictures() != null
                                ? user.getProfilePictures().stream().map(Image::getUrl).toList()
                                : Collections.emptyList()
                )
                .build();
    }

    @Override
    public LiteUserDto convertToLiteDto(User user) {
        String profilePictureUrl = "";
        List<Image> pictures = user.getProfilePictures();
        if (pictures != null && !pictures.isEmpty()) {
            profilePictureUrl = pictures.get(0).getUrl();
        }
        return LiteUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarUrl(profilePictureUrl)
                .build();
    }
}
