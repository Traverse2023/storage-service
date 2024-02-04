package com.traverse.storage.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Channel {
    private String name;
    private ArrayList<Message> messages;
    private long messageCount;

    public Channel(String name) {
        this.name = name;
        this.messages = new ArrayList<Message>();
        this.messageCount = 0;
    }

    public void addMsg(Message msg) {
        this.messages.add(0, msg);
    }

}
