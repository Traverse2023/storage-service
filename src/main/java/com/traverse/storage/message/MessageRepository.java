package com.traverse.storage.message;

import com.traverse.storage.models.Message;
import com.traverse.storage.models.MessageList;
import com.traverse.storage.models.MessageType;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;

public interface MessageRepository {

    Message updateMessage(final String primaryKey, final String sortKey, final String newText, final String newMedia);

    Message deleteMessage(final Message message);

    Message createMessage(final Message message);

    MessageList getMessages(String groupId, String channelName, String paginationToken) throws MessagesNotFoundException;

    Message editMessage();

    Message getMessage();

    Message deleteChat();

    Message deleteGroup();


}
