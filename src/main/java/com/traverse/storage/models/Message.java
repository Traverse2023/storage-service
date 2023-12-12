package com.traverse.storage.group.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Message {
    private String author;
    private LocalDateTime time;
    private String message;

    public Message(String author, String message) {
        this.author = author;
        this.message = message;
        this.time = LocalDateTime.now();
    }
}
