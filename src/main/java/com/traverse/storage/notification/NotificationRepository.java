package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import com.traverse.storage.models.NotificationList;

public interface NotificationRepository {
    Notification deleteNotification(Notification notification);

    Notification createNotification(Notification notification);

    NotificationList getNotifications(String userId, String cursor);

}
