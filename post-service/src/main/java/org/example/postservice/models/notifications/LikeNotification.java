package org.example.postservice.models.notifications;

import lombok.*;
import org.example.postservice.models.NOTIFICATION_STATUS;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LikeNotification extends NotificationMessage {
    private Long postId;
    private Long likerId;

    public LikeNotification(Long userId, LocalDateTime createdAt, NOTIFICATION_STATUS status, Long postId, Long likerId) {
        super(userId, createdAt, status);
        this.postId = postId;
        this.likerId = likerId;
    }
}