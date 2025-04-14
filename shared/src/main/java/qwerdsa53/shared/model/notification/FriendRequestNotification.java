package qwerdsa53.shared.model.notification;

import lombok.*;
import qwerdsa53.shared.model.type.NOTIFICATION_STATUS;

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