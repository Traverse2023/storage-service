package com.traverse.storage.group.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Channel {
    private String name;
    private ArrayList<Message> messages;

    public Channel(String name, ArrayList<Message> messages) {
        this.name = name;
        this.messages = messages;
    }
}
