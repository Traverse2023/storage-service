package com.traverse.storage.message;

import com.traverse.storage.dynamoModels.Message;
import com.traverse.storage.models.MessageType;
import com.traverse.storage.models.NotificationType;
import org.springframework.data.repository.CrudRepository;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.io.ByteArrayInputStream;
import java.time.ZonedDateTime;
import java.util.List;

public interface MessageRepository {

    public Message updateMessage(final String primaryKey, final String sortKey, final String newText, final String newMedia);

    public void deleteMessage(final String primaryKey, final String sortKey);

    public Message createMessage(
            final String groupId,
            final String channelName,
            final MessageType type,
            final String id,
            final ZonedDateTime created,
            final String sender,
            final String text,
            final List<String> mediaURLs
    );

    public PageIterable<Message> getMessages(String groupId, String channelName);
}
