package com.traverse.storage.message;

import com.traverse.storage.dynamoModels.Message;

import java.util.List;

public class MessageDynamoDBRepository implements MessageRepository {
    @Override
    public Message updateMessage(final String primaryKey, final String sortKey, final String newText, final String newMedia) {
        return null;
    }

    @Override
    public void deleteMessage(final String primaryKey, final String sortKey) {

    }

    @Override
    public Message createMessage(final String channel, final String type, final String id, final String created, final String sender, final String recipient, final String senderPfp, final String text, final String media) {
        return null;
    }

    @Override
    public List<Message> getMessages(final String channel) {
        return null;
    }
}
