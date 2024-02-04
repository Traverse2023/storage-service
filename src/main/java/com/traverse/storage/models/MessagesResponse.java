package com.traverse.storage.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessagesResponse {
    private long messageCount;
    private List<Message> messages;
}
