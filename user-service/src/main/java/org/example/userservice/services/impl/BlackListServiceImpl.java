package org.example.userservice.services.impl;

import org.example.userservice.services.BlackListService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class BlackListServiceImpl implements BlackListService {
    private final RedisTemplate<String, String> redisTemplate;

    public BlackListServiceImpl(@Qualifier("jwtRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToBlacklist(String token, long expirationTimeInMillis) {
        redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofMillis(expirationTimeInMillis));
    }
}
