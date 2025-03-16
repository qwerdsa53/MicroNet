package org.example.userservice.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class UserStatusListener {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    public UserStatusListener(@Qualifier("onlineUsersRedisTemplate") RedisTemplate<String, String> redisTemplate,
                              UserRepository userRepository) {
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "status", groupId = "user-service-group")
    public void listen(@Payload String message) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(message);
            Long userId = jsonNode.get("userId").asLong();
            boolean isOnline = jsonNode.get("online").asBoolean();

            log.info("Updating status for user {}: online={}", userId, isOnline);

            redisTemplate.opsForValue().set("user:online:" + userId, String.valueOf(isOnline));

            if (!isOnline) {
                userRepository.updateLastSeen(userId, LocalDateTime.now());
            }

        } catch (Exception e) {
            log.error("Error processing Kafka message: {}", e.getMessage());
        }
    }
}
