package com.traverse.storage.notification;

import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import com.traverse.storage.models.Notification;
import com.traverse.storage.notification.NotificationRepository;

import com.traverse.storage.utils.exceptions.mongo.MongoDBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.where;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Notification createNotification(Notification notification) {
        return repository.insert(notification);
    }

    public List<Notification> getNotificationsByPage(String recipientEmail, Pageable pageable) {
        Page<Notification> notifications = repository.findByRecipient(recipientEmail, pageable);
        log.info("Notifications for recipient {} : {}", recipientEmail, notifications.getContent());
        return notifications.getContent();
        // TODO: Exception handling
    }
}
