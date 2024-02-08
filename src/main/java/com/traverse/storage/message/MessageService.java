package com.traverse.storage.message;

import com.traverse.storage.group.GroupRepository;
import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import com.traverse.storage.models.MessagesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import java.util.*;


@Slf4j
@Service
public class MessageService {

    @Autowired
    private GroupRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public void saveMessage(Message message) {
        // TODO: Optimize Mongo operation to not pull into memory entire group doc.
        //  Return Message object created only to be used in editing and deletion for front end
        Optional<Group> groupDoc = repository.findById(message.getGroupId());
        message.setId(String.valueOf(UUID.randomUUID()));
        if (groupDoc.isPresent()) {
            Group group = groupDoc.get();

            // Update the specific field in the map
            Channel channel = group.getChannels().get(message.getChannelName());
            channel.addMsg(message);
            // Update message count
            channel.setMessageCount(channel.getMessageCount() + 1);

            // Save the updated document
            repository.save(group);
        } else {
            // TODO: Exception handling
        }
    }


    // Retrieve paginated messages for specified group-channel
    public MessagesResponse getMessages(String groupId, String channelName, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10);
        Channel channel = repository.findGroupMessages(
                groupId, channelName, pageable.getPageSize() * (pageable.getPageNumber() - 1), pageable.getPageSize())
                .get(0).getChannels().get(channelName);
        log.info(channel.toString());
        long messageCount = channel.getMessageCount();
        List<Message> messages = channel.getMessages();
        Collections.reverse(messages);
        log.info("Retrieved messages: {}. Message count {}", messages, messageCount);
        return new MessagesResponse(messageCount, messages);
        // TODO: implement exception handling and edge cases
    }

}
