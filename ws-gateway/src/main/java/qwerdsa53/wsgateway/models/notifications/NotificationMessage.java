package qwerdsa53.wsgateway.models.notifications;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qwerdsa53.wsgateway.models.NOTIFICATION_STATUS;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FriendRequestNotification.class, name = "FRIEND_REQUEST"),
        @JsonSubTypes.Type(value = MessageNotification.class, name = "MESSAGE"),
        @JsonSubTypes.Type(value = LikeNotification.class, name = "LIKE")
})
public abstract class NotificationMessage {
    private String id;
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private NOTIFICATION_STATUS status;
}
