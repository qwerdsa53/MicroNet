package qwerdsa53.wsgateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import qwerdsa53.wsgateway.models.dto.StatusUpdateDTO;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final WebSocketSessionManager sessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = extractUserId(session);
        if (userId == null) {
            log.warn("User ID is null, closing session.");
            closeSession(session);
            return;
        }

        sessionManager.addSession(userId, session);
        sendStatusToKafka(userId, true);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = extractUserId(session);
        if (userId != null) {
            sessionManager.removeSession(userId);
            sendStatusToKafka(userId, false);
        }
    }

    private void sendStatusToKafka(String userId, boolean isOnline) {
        try {
            StatusUpdateDTO statusUpdateDTO = StatusUpdateDTO.builder()
                    .userId(Long.parseLong(userId))
                    .online(isOnline)
                    .timestamp(LocalDateTime.now())
                    .build();
            String payload = objectMapper.writeValueAsString(statusUpdateDTO);
            kafkaTemplate.send("status", payload);
            log.info("Sent status update to Kafka: {}", payload);
        } catch (JsonProcessingException e) {
            log.error("Error sending status update", e);
        }
    }

    private String extractUserId(WebSocketSession session) {
        try {
            String path = Objects.requireNonNull(session.getUri()).getPath();
            String[] segments = path.split("/");

            if (segments.length < 3) {
                log.warn("Invalid WebSocket path: {}", path);
                return null;
            }

            return segments[2];
        } catch (Exception e) {
            log.error("Error extracting user ID", e);
            return null;
        }
    }

    private void closeSession(WebSocketSession session) {
        try {
            session.close(CloseStatus.BAD_DATA);
        } catch (Exception e) {
            log.error("Error closing WebSocket session", e);
        }
    }
}