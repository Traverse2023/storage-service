package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Notification createNotification(Notification notification) {
        return repository.insert(notification);
        // TODO: Exception handling
    }

    public List<Notification> getNotificationsByPage(String recipientEmail, Pageable pageable) {
        Page<Notification> notifications = repository.findByRecipient(recipientEmail, pageable);
        log.info("Notifications for recipient {} : {}", recipientEmail, notifications.getContent());
        return notifications.getContent();
        // TODO: Exception handling
    }

    public Notification deleteNotification(String id) {
        Notification notification = repository.deleteNotificationById(id);
        log.info("Successfully deleted notification: {}\n{}", notification.getId(), notification.getMessage());
        return notification;
        // TODO: Exception handling
    }
}
