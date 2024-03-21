package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import com.traverse.storage.models.NotificationList;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    NotificationController(NotificationService service) {this.notificationService = service;}

    /**
     *
     *
     * @param requestBody json containing a notification object to be stored in DB
     * @return Notification object back to front end to be used for future calls on stored notification
     * */
    @PostMapping("/createNotification")
    public Notification createNotification(@RequestBody Notification notification) {
        log.info("Creating notification: {}", notification);
        return notificationService.createNotification(notification);
    }

    /**
     *
     *
     * @param recipientEmail the email of the user who the notification is to be sent to
     * @param pageNumber the pagination page number of results that should be fetched from the database
     * @return A list of notifications representing a single paginated result out of all notifications
     * */
    @GetMapping("/getNotifications")
    public NotificationList getNotifications(@RequestBody String requestBody) {
        JSONObject jsonBody = new JSONObject(requestBody);
        String userId = jsonBody.getString("userId");
        String cursor = jsonBody.optString("cursor");
        log.info("Getting notifications for user: {}", userId);
        return notificationService.getNotifications(userId, cursor);
    }

    /**
     *
     * @param Notification The database id of the notification to be deleted
     * @return The notification that was deleted if it was deleted successfully
     * */
    @DeleteMapping("/deleteNotification")
    public Notification deleteNotification(@RequestBody Notification notification) {
        log.info("Deleting notification: {}", notification);
        return notificationService.deleteNotification(notification);
    }

}
