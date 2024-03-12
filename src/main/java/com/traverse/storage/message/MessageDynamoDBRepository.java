package com.traverse.storage.message;

import com.traverse.storage.dynamoModels.Message;
import com.traverse.storage.models.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.time.ZonedDateTime;
import java.util.List;

import static software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo;

@Slf4j
@Repository
public class MessageDynamoDBRepository implements MessageRepository {

    // TODO: Implement try catches. May result in DynamoDbException for accessors

    private DynamoDbClient client;
    private final DynamoDbTable<Message> table;
    @Autowired
    MessageDynamoDBRepository(final DynamoDbEnhancedClient client) {
        this.table = client.table("Messages", TableSchema.fromBean(Message.class));
    }

    @Override
    public Message updateMessage(
            final String partitionKey, final String sortKey, final String newText, final String newMedia
    ) {
        return null;
    }

    @Override
    public void deleteMessage(final String partitionKey, final String sortKey) {
        Key key = Key.builder()
                .partitionValue(partitionKey)
                .sortValue(sortKey)
                .build();
        DeleteItemEnhancedRequest request = DeleteItemEnhancedRequest.builder()
                .key(key)
                .build();

        Message deletedMessage = table.deleteItem(request);
        log.info("Message successfully deleted: {}", deletedMessage.getSortKey());
    }


    @Override
    public Message createMessage(
            final String groupId, final String channelName, final MessageType type,
            final String id, final ZonedDateTime created, final String sender,
            final String text, final List<String> mediaURLs
    ) {
        final String pk = String.format("%s#%s", groupId, channelName);

        final String sk = String.format("%s#%s", created, id);

        final Message message = Message.builder()
                .partitionKey(pk)
                .sortKey(sk)
                .id(id)
                .type(type)
                .created(created)
                .sender(sender)
                .text(text)
                .mediaURLs(mediaURLs)
                .updated(created)
                .build();

        table.putItem(message);
        log.info("Message successfully created: {}", message.getSortKey());
        return message;
    }



    @Override
    public PageIterable<Message> getMessages(final String groupId, final String channelName) {
        return table.query(keyEqualTo(k -> k.partitionValue(String.format("%s#%s", groupId, channelName))));

        //TODO: paginate
    }

}