package com.traverse.storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Channel {
    private String name;
    private List<Message> messages;
    private long messageCount;

    public Channel(String name) {
        this.name = name;
        this.messages = new ArrayList<>();
        this.messageCount = 0;
    }

    public void addMsg(Message msg) {
        this.messages.add(0, msg);
    }

}
