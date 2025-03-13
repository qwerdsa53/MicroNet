package org.example.userservice.services;

import org.example.userservice.model.dto.LiteUserDto;
import org.springframework.data.domain.Page;

public interface BlackListService {
    void addToBlackList(Long requester, Long target);

    void removeFromBlackList(Long requester, Long target);

    Page<LiteUserDto> getBlackList(Long userId, int page, int size);
}
