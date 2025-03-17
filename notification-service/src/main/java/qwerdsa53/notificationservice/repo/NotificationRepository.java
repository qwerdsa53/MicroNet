package qwerdsa53.notificationservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import qwerdsa53.notificationservice.models.notifications.NotificationMessage;

import java.util.List;

public interface NotificationRepository extends MongoRepository<NotificationMessage, String> {
    List<NotificationMessage> findByUserId(Long userId);
}