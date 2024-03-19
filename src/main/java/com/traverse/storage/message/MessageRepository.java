package com.traverse.storage.message;

import com.traverse.storage.models.Message;
import com.traverse.storage.models.MessageList;
import com.traverse.storage.models.MessageType;

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

    public MessageList getMessages(String groupId, String channelName, String paginationToken);
}
