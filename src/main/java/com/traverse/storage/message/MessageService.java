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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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


    public Message createMessage(final Message message) {
        final String id = UUID.randomUUID().toString();
        final String created = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());

        final Message newMessage = message.toBuilder()
                .id(id)
                .created(created)
                .updated(created)
                .build();
        return messageRepository.createMessage(newMessage);
    }

    // Retrieve paginated messages for specified group-channel
    public MessageList getMessages(String groupId, String channelName, String paginationToken) throws MessagesNotFoundException {
        return messageRepository.getMessages(groupId, channelName, paginationToken);

    }

    public Message editMessage() {
        return messageRepository.editMessage();
    }

    public Message getMessage() {
        return messageRepository.getMessage();
    }

    public Message deleteMessage(final Message message) {
        return messageRepository.deleteMessage(message);
    }

    public void deleteChat() {
        messageRepository.deleteChat();
    }

    public void deleteGroup() {
        messageRepository.deleteGroup();
    }

}
