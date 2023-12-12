package com.traverse.storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
public class Message {
    private ChatMessageType type;
    private String email;
    private String author;
    private String message;
    private LocalDateTime dateTime;
    private byte[] attachment;
    private String groupId;
    private String channelName;

}
