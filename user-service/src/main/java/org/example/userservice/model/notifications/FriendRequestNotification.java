package org.example.userservice.model.notifications;

import lombok.*;
import org.example.userservice.model.NOTIFICATION_STATUS;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FriendRequestNotification extends NotificationMessage {
    private Long senderId;

    public FriendRequestNotification(Long userId, Long senderId, LocalDateTime createdAt, NOTIFICATION_STATUS status) {
        super(userId, createdAt, status);
        this.senderId = senderId;
    }
}