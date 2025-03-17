package qwerdsa53.wsgateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketSessionManager {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session) {
        sessions.put(userId, session);
        log.info("User {} connected.", userId);
    }

    public void removeSession(String userId) {
        sessions.remove(userId);
        log.info("User {} disconnected.", userId);
    }

    public Optional<WebSocketSession> getSession(Long userId) {
        return Optional.ofNullable(sessions.get(userId.toString()));
    }
}