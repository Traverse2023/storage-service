package com.traverse.storage.notification;


import com.traverse.storage.models.Notification;
import com.traverse.storage.models.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     *
     *
     * @param requestBody json containing a notification object to be stored in DB
     * @return Notification object back to front end to be used for future calls on stored notification
     * */
    @PostMapping("/createNotification")
    public Notification createNotification(@RequestBody String requestBody) {
        System.out.println("REQUEST BODY: " + requestBody);
        JSONObject jsonBody = new JSONObject(requestBody);

        Notification notification = Notification.builder()
                .recipient(jsonBody.getString("recipientEmail"))
                .groupId(jsonBody.optString("groupId"))
                .groupName(jsonBody.optString("groupName"))
                .notificationType(NotificationType.fromString(jsonBody.getString("notificationType")))
                .message(jsonBody.getString("message"))
                .time(LocalDateTime.now())
                .sender(jsonBody.optString("senderEmail"))
                .build();
        return notificationService.createNotification(notification);
        // TODO: communicate exceptions
    }

    /**
     *
     *
     * @param recipientEmail the email of the user who the notification is to be sent to
     * @param pageNumber the pagination page number of results that should be fetched from the database
     * @return A list of notifications representing a single paginated result out of all notifications
     * */
    @GetMapping("/getNotifications/{recipientEmail}/{pageNumber}")
    public List<Notification> getNotifications(@PathVariable String recipientEmail, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return notificationService.getNotificationsByPage(recipientEmail, pageable);
        // TODO: communicate exceptions
    }

    /**
     *
     * @param notificationId The database id of the notification to be deleted
     * @return The notification that was deleted if it was deleted successfully
     * */
    @DeleteMapping("/deleteNotification/{notificationId}")
    public Notification deleteNotification(@PathVariable String notificationId) {
        return notificationService.deleteNotification(notificationId);
        // TODO: communicate exceptions
    }

}
