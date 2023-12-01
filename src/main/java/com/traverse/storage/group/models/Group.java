package com.traverse.storage.group.models;

import com.traverse.storage.group.models.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "group")
public class Group {
    @Id
    private String id;
    private String name;
    private ArrayList<Channel> channels;

    public Group(ArrayList<Channel> channels) {
        this.channels = channels;
    }
    public Group(String name) {
        this.name = name;
        Channel channel = new Channel("general");
        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(channel);
        this.channels = channels;
    }
}


