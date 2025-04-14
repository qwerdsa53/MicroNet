package qwerdsa53.notificationservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qwerdsa53.notificationservice.repo.NotificationRepository;
import qwerdsa53.notificationservice.services.NotificationService;
import qwerdsa53.shared.model.NOTIFICATION_STATUS;
import qwerdsa53.shared.model.notifications.NotificationMessage;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationMessage saveNotification(NotificationMessage notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(NOTIFICATION_STATUS.UNREAD);
        return notificationRepository.save(notification);
    }

    @Override
    public List<NotificationMessage> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}