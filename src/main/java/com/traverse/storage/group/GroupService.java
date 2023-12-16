package com.traverse.storage.group;


import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import org.springframework.beans.factory.annotation.Autowired;

<<<<<<< HEAD
=======
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
>>>>>>> farhan-changes-create-group
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.traverse.storage.group.GroupRepository;

@Service
public class GroupService {

    @Autowired
    private MongoTemplate mongoTemplate;





    public List<Group> getGroups() {

        Message msg = new Message();
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
