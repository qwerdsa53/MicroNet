package org.example.userservice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserRedisService {
    private final RedisTemplate<Long, UserDto> redisTemplate;

    public UserRedisService(@Qualifier("cacheRedisTemplate") RedisTemplate<Long, UserDto> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Async
    public void saveUser(Long key, UserDto userDto, long ttl) {
        log.info("Saving user in thread: {}", Thread.currentThread().getName());
        redisTemplate.opsForValue().set(key, userDto, ttl, TimeUnit.SECONDS);
    }

    public void deleteUser(Long key) {
        redisTemplate.delete(key);
    }

    public void updateTTL(Long key, long ttlInSeconds) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.expire(key, ttlInSeconds, TimeUnit.SECONDS);
        }
    }

    public boolean userExists(Long key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public UserDto getUser(Long key) {
        return redisTemplate.opsForValue().get(key);
    }
}
