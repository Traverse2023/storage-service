package com.traverse.storage.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Channel {
    private String name;
    private ArrayList<Message> messages;

    public Channel(String name) {
        this.name = name;
        this.messages = new ArrayList<Message>();
    }
}
