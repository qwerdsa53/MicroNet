package org.example.userservice.mapper;

import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;
import org.mapstruct.*;
import qwerdsa53.shared.model.entity.Image;
import qwerdsa53.shared.model.entity.User;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Collections.class})
public interface UserMapper {

    @Mapping(target = "profilePictures", source = "user.profilePictures", qualifiedByName = "mapProfilePictures")
    UserDto convertToDto(User user, @Context Boolean isOnline);

    @Mapping(target = "avatarUrl", source = "user", qualifiedByName = "mapAvatarUrl")
    LiteUserDto convertToLiteDto(User user, @Context Boolean isOnline);

    @AfterMapping
    default void setIsOnline(User user, @Context Boolean isOnline, @MappingTarget UserDto userDto) {
        userDto.setIsOnline(isOnline);
    }

    @AfterMapping
    default void setIsOnlineLite(User user, @Context Boolean isOnline, @MappingTarget LiteUserDto dto) {
        dto.setIsOnline(isOnline);
    }

    @Named("mapProfilePictures")
    static List<String> mapProfilePictures(List<Image> images) {
        if (images == null) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(Image::getUrl)
                .toList();
    }

    @Named("mapAvatarUrl")
    static String mapAvatarUrl(User user) {
        List<Image> pictures = user.getProfilePictures();
        if (pictures != null && !pictures.isEmpty()) {
            return pictures.get(0).getUrl();
        }
        return "";
    }
}