package org.example.userservice.utiles;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final RedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public WebSocketHandler(@Qualifier("onlineUsersRedisTemplate") RedisTemplate<String, String> redisTemplate,
                            UserRepository userRepository) {
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = Objects.requireNonNull(session.getUri()).getPath().split("/ws/")[1];
        sessions.put(userId, session);

        redisTemplate.opsForValue().set("user:online:" + userId, "true");

        log.info("User {} connected", userId);
        scheduleLastSeenUpdate(userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = Objects.requireNonNull(session.getUri()).getPath().split("/ws/")[1];
        sessions.remove(userId);

        redisTemplate.opsForValue().set("user:online:" + userId, "false");

        userRepository.updateLastSeen(Long.parseLong(userId), LocalDateTime.now());

        System.out.println("User " + userId + " disconnected");
    }

    private void scheduleLastSeenUpdate(String userId) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            if (sessions.containsKey(userId)) {
                userRepository.updateLastSeen(Long.parseLong(userId), LocalDateTime.now());
            }
        }, 5, 5, TimeUnit.MINUTES);
    }
}