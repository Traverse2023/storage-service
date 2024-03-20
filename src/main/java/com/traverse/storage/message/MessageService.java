package com.traverse.storage.message;

import com.traverse.storage.models.Message;
import com.traverse.storage.models.MessageList;
import com.traverse.storage.models.MessageType;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Builder
public class MessageService {

    private MessageRepository messageRepository;
    @Autowired
    MessageService(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(final String groupId, final String channelName, final MessageType type,
                            final String id, final ZonedDateTime created, final String sender,
                            final String text, final List<String> mediaURLs) {

        return messageRepository.createMessage(groupId, channelName, type, id, created, sender, text, mediaURLs);
    }


    // Retrieve paginated messages for specified group-channel
    public MessageList getMessages(String groupId, String channelName, String paginationToken) throws MessagesNotFoundException {
        return messageRepository.getMessages(groupId, channelName, paginationToken);

    }

}
