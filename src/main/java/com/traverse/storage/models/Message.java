package com.traverse.storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String text;
    private LocalDateTime time;
    //private byte[] attachment;
    private String groupId;
    private String channelName;
    private String pfpURL;
}

/**
 * {"email":"briplomo@gmail.com","author":"Bryan","message":"This is bryan's message from spring.","dateTime":[2023,12,12,18,38,49,286815000],"attachment":"","groupId":"65728e07e51fdf72df913759","channelName":"general"}

 * */
