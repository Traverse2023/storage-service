package com.traverse.storage.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document
public class Group {
    @Id
    private String id;
    private ArrayList<Channel> channels;

    public Group(ArrayList<Channel> channels) {
        this.channels = channels;
    }
}
