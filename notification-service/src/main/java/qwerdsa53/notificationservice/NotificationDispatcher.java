package qwerdsa53.notificationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import qwerdsa53.notificationservice.models.notifications.NotificationMessage;
import qwerdsa53.notificationservice.services.NotificationService;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationDispatcher {
    private final NotificationService notificationService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "notifications", groupId = "notification-group")
    public void handleNotification(String message) {
        try {
            NotificationMessage notification = objectMapper.readValue(message, NotificationMessage.class);
            log.info("Received notification: {}", notification);
            notificationService.saveNotification(notification);
            String payload = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(notification);
            kafkaTemplate.send("ws_messages", payload);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse notification", e);
        }
    }
}