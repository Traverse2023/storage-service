package com.traverse.storage.message;

import com.traverse.storage.dynamoModels.Message;

import java.util.Date;
import java.util.List;

public interface MessageRepository {

    public Message updateMessage(final String primaryKey, final String sortKey, final String newText, final String newMedia);

    public void deleteMessage(final String primaryKey, final String sortKey);

    public Message createMessage(
            final String channel,
            final String type,
            final String id,
            final String created,
            final String sender,
            final String recipient,
            final String senderPfp,
            final String text,
            final String media
    );

    public List<Message> getMessages(final String channel);
}
