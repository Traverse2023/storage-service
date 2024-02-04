package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    Page<Notification> findByRecipient(String recipient, Pageable pageable);

    @Query(value = "{ 'id': $0}", delete = true)
    Notification deleteNotificationById(String id);

}
