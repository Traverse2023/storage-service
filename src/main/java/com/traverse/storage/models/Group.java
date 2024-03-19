//package com.traverse.storage.models;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Document(collection = "group")
//public class Group {
//    @Id
//    private String id;
//    private String name;
//    private Map<String, Channel> channels;
//
//    public Group(Map<String, Channel> channels) {
//        this.channels = channels;
//    }
//    public Group(String name) {
//        this.name = name;
//        Channel channel = new Channel("general");
//        Map<String, Channel> channels = new HashMap<>();
//        channels.put("general", channel);
//        this.channels = channels;
//    }
//}
//

