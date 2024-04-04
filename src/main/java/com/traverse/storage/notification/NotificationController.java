package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import com.traverse.storage.models.NotificationList;
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
     * @param notification  json containing a notification object to be created and stored in database
     * @return     object back to front end to be used for future calls on stored notification
     * */
    @PostMapping("/createNotification")
    public Notification createNotification(@RequestBody Notification notification) {
        log.info("Creating notification: {}", notification);
        return notificationService.createNotification(notification);
    }

    /**
     *
     *
     * @param userId the UUID of the user who the notification is sent to
     * @param cursor the pagination implementation to dictate the next set of messages to be retrieved.
     *               Only forward pagination is enabled.
     * @return A list of notifications representing a single paginated result out of all notifications
     * */
    @GetMapping("/getNotifications/{userId}")
    public NotificationList getNotifications(@PathVariable String userId, @RequestParam(defaultValue = "") String cursor) {
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
