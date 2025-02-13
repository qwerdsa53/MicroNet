package org.example.userservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
public class TokenServiceImpl {

    private final RedisTemplate<String, String> redisTemplate;

    public TokenServiceImpl(@Qualifier("uuidRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String token, String email) {
        redisTemplate.opsForValue().set("confirmationToken:" + token, email, Duration.ofHours(24));
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String addTokenToRedis(String email) {
        String token = generateToken();
        saveToken(token, email);
        return token;
    }

    public String getUserEmailByToken(String token) {
        return redisTemplate.opsForValue().get("confirmationToken:" + token);
    }

    public void deleteToken(String token) {
        redisTemplate.delete("confirmationToken:" + token);
    }
}