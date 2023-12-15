package com.traverse.storage.message;

import com.traverse.storage.group.GroupRepository;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.where;

@Service
public class MessageService {

    @Autowired
    private GroupRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public void saveMessage(Message message) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(message.getGroupId())
                .and("channels.name").is(message.getChannelName()));
        Update update = new Update().push("messages", message);
        mongoTemplate.updateFirst(query, update, Group.class);
    }
}
