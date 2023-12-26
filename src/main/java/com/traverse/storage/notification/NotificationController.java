package com.traverse.storage.notification;


import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import com.traverse.storage.models.Notification;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/createNotification")
    public Notification createNotification(@RequestBody String requestBody) {
        System.out.println("REQUEST BODY: " + requestBody);
        JSONObject jsonBody = new JSONObject(requestBody);
        String forEmail = jsonBody.getString("forEmail");
        String eventType = jsonBody.getString("eventType");
        String notificationMessage = jsonBody.getString("notificationMessage");
        Notification notif = Notification.builder()
                .forEmail(forEmail)
                .eventType(eventType)
                .notificationMessage(notificationMessage)
                .build();
        return notificationService.addNotificationToDb(notif);
    }

    @GetMapping("/getNotifications/{forEmail}")
    public List<Notification> getNotifications(@PathVariable String forEmail) {
        return notificationService.getNotifications(forEmail);
    }

    @SqsListener(value = "${cloud.aws.end_point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessageFromQueue(Notification notif) {
        log.info("Receiving message from SQS...\n{}", notif.toString());
        notificationService.addNotificationToDb(notif);
    }
}
