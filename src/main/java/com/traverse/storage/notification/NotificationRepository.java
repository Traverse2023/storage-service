package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import com.traverse.storage.models.NotificationList;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;

public interface NotificationRepository {
    Notification deleteNotification(Notification notification);

    Notification createNotification(Notification notification);

    NotificationList getNotifications(String userId, String cursor) throws MessagesNotFoundException;

}
