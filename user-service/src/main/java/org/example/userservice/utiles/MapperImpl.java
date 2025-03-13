package org.example.userservice.utiles;

import org.example.userservice.model.Image;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperImpl implements Mapper {
    @Override
    public LiteUserDto convertToDto(User user) {
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
