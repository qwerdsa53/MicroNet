package org.example.userservice.services;

public interface BlackListService {
    void addToBlacklist(String token, long expirationTimeInMillis);
}
