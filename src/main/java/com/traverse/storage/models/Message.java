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
    private String firstName;
    private String lastName;
    private String text;
    private LocalDateTime time;
    private String pfpURL;
    //private byte[] attachment;
    private String groupId;
    private String channelName;
    private String pfpURL;
}

/**
 * {"email":"briplomo@gmail.com","author":"Bryan","message":"This is bryan's message from spring.","dateTime":[2023,12,12,18,38,49,286815000],"attachment":"","groupId":"65728e07e51fdf72df913759","channelName":"general"}

 * */
