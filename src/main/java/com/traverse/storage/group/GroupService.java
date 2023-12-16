package com.traverse.storage.group;


import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
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

    public Group addGroup(String name) {
        return mongoTemplate.insert(new Group(name));
    }
}
