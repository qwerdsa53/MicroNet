package qwerdsa53.notificationservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import qwerdsa53.shared.model.notifications.NotificationMessage;

import java.util.List;

public interface NotificationRepository extends MongoRepository<NotificationMessage, String> {
    List<NotificationMessage> findByUserId(Long userId);
}