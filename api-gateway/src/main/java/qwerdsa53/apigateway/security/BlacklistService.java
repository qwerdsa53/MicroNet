package qwerdsa53.apigateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlacklistService {
    private final RedisTemplate<String, String> redisTemplate;

    public BlacklistService(@Qualifier("jwtRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isTokenBlacklisted(String token) {
        try {
            log.info("Checking if token is blacklisted: {}", token);
            boolean isBlacklisted = Boolean.TRUE.equals(redisTemplate.hasKey(token));
            log.info("Blacklist check result: {}", isBlacklisted);
            return isBlacklisted;
        } catch (Exception e) {
            log.error("Failed to check token blacklist: {}", e.getMessage(), e);
            throw e;
        }
    }
}
