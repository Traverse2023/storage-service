package com.traverse.storage.message;

import com.traverse.storage.group.GroupRepository;
import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Slf4j
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
            // TODO: Errors
            // Handle the case where the document with the given ID is not found
            // You may throw an exception, log a message, etc.
        }
    }



    // Retrieve paginated messages for specified group-channel
    public List<Message> getMessages(String groupId, String channelName, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 5);
        List<Group> groupDocument = repository.findGroupMessages(groupId, channelName, pageable.getPageSize() * (pageable.getPageNumber() - 1), pageable.getPageSize());
        log.info(groupDocument.toString());
        List<Message> messages = groupDocument.get(0).getChannels().get(channelName).getMessages();
        log.info("Retrieved messages: ${}", messages);
        return messages;
        // TODO: implement exception handling and edge cases
    }


}
