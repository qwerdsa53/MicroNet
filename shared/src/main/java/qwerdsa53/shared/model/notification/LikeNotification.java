package qwerdsa53.shared.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import qwerdsa53.shared.model.type.NOTIFICATION_STATUS;

import java.time.LocalDateTime;

@Data
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