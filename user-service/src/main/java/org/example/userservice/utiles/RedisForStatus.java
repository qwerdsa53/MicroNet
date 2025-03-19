package org.example.userservice.utiles;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RedisForStatus {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisForStatus(@Qualifier("onlineUsersRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Optional<Boolean> isOnline(String keyWithId) {
        return Optional.of(Boolean.valueOf(redisTemplate.opsForValue().get(keyWithId)));
    }
}
