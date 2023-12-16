package com.traverse.storage.group;

import com.traverse.storage.group.models.Channel;
import com.traverse.storage.group.models.Group;
import com.traverse.storage.group.models.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.traverse.storage.group.GroupRepository;

@Service
public class GroupService {

    @Autowired
    private MongoTemplate mongoTemplate;


//    @Autowired
//    public GroupService() {
//
//    }


    public List<Group> getGroups() {
        Message msg = new Message("isfar", LocalDateTime.now(), "Hey");
        ArrayList<Message> msgs = new ArrayList<Message>();
        msgs.add(msg);
        Channel channel = new Channel("general");
        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(channel);
        Group group = new Group(channels);

        return mongoTemplate.findAll(Group.class);
    }

    public Group addGroup(String name) {
        return mongoTemplate.insert(new Group(name));
    }
}
