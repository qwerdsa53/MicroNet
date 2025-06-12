package org.example.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.repo.UserRepository;
import org.springframework.stereotype.Service;
import qwerdsa53.shared.model.entity.User;

@Service
@RequiredArgsConstructor
public class UserAccessService {
    private final UserRepository userRepository;
    private final String ID_EXCEPTION_TEMP = "User with id = %d not found";
    private final String EMAIL_EXCEPTION_TEMP = "User with email = %s not found";

    public User getByIdOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(ID_EXCEPTION_TEMP.formatted(id)));
    }

    public User getByIdWithLockOrThrow(Long id) {
        return userRepository.findUserWithLock(id).orElseThrow(() ->
                new UserNotFoundException(ID_EXCEPTION_TEMP.formatted(id)));
    }

    public User getByEmailOrThrow(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(EMAIL_EXCEPTION_TEMP.formatted(email)));
    }
}
