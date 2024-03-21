package com.traverse.storage.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traverse.storage.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationList {
    private String cursor;
    private List<Notification> notifications;
}
