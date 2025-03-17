package qwerdsa53.wsgateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import qwerdsa53.wsgateway.models.notifications.NotificationMessage;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketNotificationListener {
    private final WebSocketSessionManager sessionManager;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ws_messages", groupId = "ws-group")
    public void handleNotification(String message) {
        try {
            NotificationMessage notification = objectMapper.readValue(message, NotificationMessage.class);
            Optional<WebSocketSession> sessionOpt = sessionManager.getSession(notification.getUserId());


            sessionManager.getSession(notification.getUserId()).ifPresent(session -> processNotification(notification, session));

        } catch (JsonProcessingException e) {
            log.error("Failed to parse notification message", e);
        }
    }

    private void processNotification(NotificationMessage notification, WebSocketSession session) {
        if (!session.isOpen()) {
            log.warn("⚠️ WebSocket session for user {} is closed, cannot send notification.", notification.getUserId());
            return;
        }
        try {
            String payload = objectMapper.writeValueAsString(notification);
            session.sendMessage(new TextMessage(payload));

            log.info("Sent {} notification to user {}: {}", notification.getClass().getSimpleName(), notification.getUserId(), payload);
        } catch (IOException e) {
            log.error("Failed to send notification to user {}", notification.getUserId(), e);
        }
    }
}