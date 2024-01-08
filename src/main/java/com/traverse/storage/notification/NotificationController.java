package com.traverse.storage.notification;


import com.traverse.storage.models.Notification;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        Notification notification = Notification.builder()
                .recipient(jsonBody.getString("recipientEmail"))
                .groupId(jsonBody.getString("groupId"))
                .groupName(jsonBody.getString("groupName"))
                .notificationType(jsonBody.getString("notificationType"))
                .message(jsonBody.getString("message"))
                .build();
        return notificationService.createNotification(notification);
    }

    @GetMapping("/getNotifications/{recipientEmail}/{pageNumber}")
    public List<Notification> getNotifications(@PathVariable String recipientEmail, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1, 5);
        return notificationService.getNotificationsByPage(recipientEmail, pageable);
    }

}
