package com.traverse.storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String email;
    private String author;
    private String message;
    private LocalDateTime dateTime;
    private byte[] attachment;
    private String groupId;
    private String channelName;
}

/**
 * {"email":"briplomo@gmail.com","author":"Bryan","message":"This is bryan's message from spring.","dateTime":[2023,12,12,18,38,49,286815000],"attachment":"","groupId":"65728e07e51fdf72df913759","channelName":"general"}

 * */
