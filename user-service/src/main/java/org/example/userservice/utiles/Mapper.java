package org.example.userservice.utiles;

import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;

public interface Mapper {
    LiteUserDto convertToDto(User user);
}
