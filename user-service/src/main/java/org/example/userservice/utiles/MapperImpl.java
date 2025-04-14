package org.example.userservice.utiles;


import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;
import org.springframework.stereotype.Component;
import qwerdsa53.shared.model.entity.Image;
import qwerdsa53.shared.model.entity.User;

import java.util.Collections;
import java.util.List;

@Component
public class MapperImpl implements Mapper {

    @Override
    public UserDto convertToDto(User user, Boolean isOnline) {
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
                .isOnline(isOnline)
                .lastSeen(user.getLastSeen())
                .build();
    }

    @Override
    public LiteUserDto convertToLiteDto(User user, boolean isOnline) {
        String profilePictureUrl = "";
        List<Image> pictures = user.getProfilePictures();
        if (pictures != null && !pictures.isEmpty()) {
            profilePictureUrl = pictures.get(0).getUrl();
        }
        return LiteUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatarUrl(profilePictureUrl)
                .lastSeen(user.getLastSeen())
                .isOnline(isOnline)
                .build();
    }
}
