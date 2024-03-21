package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import com.traverse.storage.models.NotificationList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Slf4j
@Service
public class NotificationService {


    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(final NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(final Notification notification) {
        // Create uuid and timestamp for notification object
        final String uuid = UUID.randomUUID().toString();
        final String created = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());

        Notification newNotification = notification.toBuilder().id(uuid).created(created).build();
        return notificationRepository.createNotification(newNotification);
    }

    public NotificationList getNotifications(final String pk, final String cursor) {
        NotificationList notifications = notificationRepository.getNotifications(pk, cursor);
        log.info("Retrieved notifications.\n {}", notifications);
        return notifications;
    }

    public Notification deleteNotification(final Notification notification) {
        Notification notificationDeleted = notificationRepository.deleteNotification(notification);
        log.info("Successfully deleted notification: {}", notification);
        return notificationDeleted;
    }
}
