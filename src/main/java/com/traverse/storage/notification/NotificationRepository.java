package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    Page<Notification> findByRecipient(String recipient, Pageable pageable);

}
