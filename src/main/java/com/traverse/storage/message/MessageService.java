package com.traverse.storage.message;

import com.traverse.storage.group.GroupRepository;
import com.traverse.storage.models.Channel;
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
import java.util.Optional;

import static com.mongodb.client.model.Filters.where;

@Service
public class MessageService {

    @Autowired
    private GroupRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public void saveMessage(Message message) {

        Optional<Group> groupDoc = repository.findById(message.getGroupId());

        if (groupDoc.isPresent()) {
            Group group = groupDoc.get();

            // Update the specific field in the map
            Channel channel = group.getChannels().get(message.getChannelName());
            channel.addMsg(message);

            // Save the updated document
            repository.save(group);
        } else {
            // Handle the case where the document with the given ID is not found
            // You may throw an exception, log a message, etc.
        }
    }

//        Query query = new Query();
//        query.addCriteria(Criteria.where("_id").is(message.getGroupId())
//                .and("name").is(message.getChannelName()));
//        Update update = new Update().push("messages", message);
//        mongoTemplate.updateFirst(query, update, Group.class);
//    }

    public List<Message> getMessages(String groupId, String channelName) {
        Optional<Group> group = repository.findById(groupId);
        if(group.isPresent()) {
            Channel channel = group.get().getChannels().get(channelName);
            return channel.getMessages();
        }
        // TODO: Throw custom exception
        return null;
    }
}
