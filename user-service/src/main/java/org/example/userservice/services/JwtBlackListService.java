package org.example.userservice.services;

public interface JwtBlackListService {
    void addToBlacklist(String token, long expirationTimeInMillis);
}
