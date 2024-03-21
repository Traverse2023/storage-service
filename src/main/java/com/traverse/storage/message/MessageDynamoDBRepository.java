package com.traverse.storage.message;

import com.traverse.storage.models.Message;
import com.traverse.storage.models.MessageList;
import com.traverse.storage.models.MessageType;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;
import com.traverse.storage.utils.exceptions.serializer.InvalidTokenException;
import com.traverse.storage.utils.serializer.PaginationTokenSerializer;
import com.traverse.storage.utils.serializer.TokenSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.ListAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.StringAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.awt.event.WindowFocusListener;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primarySortKey;


@Slf4j
@Repository
public class MessageDynamoDBRepository implements MessageRepository {

    // TODO: Implement try catches. May result in DynamoDbException for accessors

    // TODO: Javadocs

    private final DynamoDbClient client;
    private final TokenSerializer<Map<String, AttributeValue>> tokenSerializer;
    private final DynamoDbTable<Message> table;
    private final String TABLE_NAME = "Messages";
    private final int PAGE_SIZE = 20;
    static final StaticTableSchema<Message> MESSAGE_STATIC_TABLE_SCHEMA =
            StaticTableSchema.builder(Message.class)
                    .newItemSupplier(Message::new)
                    .addAttribute(String.class, a -> a.name("pk")
                            .getter(Message::getPk)
                            .setter(Message::setPk)
                            .tags(primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("sk")
                            .getter(Message::getSk)
                            .setter(Message::setSk)
                            .tags(primarySortKey()))
                    .addAttribute(String.class, a -> a.name("id")
                            .getter(Message::getId)
                            .setter(Message::setId))
                    .addAttribute(MessageType.class, a -> a.name("type")
                            .getter(Message::getType)
                            .setter(Message::setType))
                    .addAttribute(String.class, a -> a.name("created")
                            .getter(Message::getCreated)
                            .setter(Message::setCreated))
                    .addAttribute(String.class, a -> a.name("sender")
                            .getter(Message::getSender)
                            .setter(Message::setSender))
                    .addAttribute(String.class, a -> a.name("text")
                            .getter(Message::getText)
                            .setter(Message::setText))
                    .addAttribute(EnhancedType.listOf(String.class), a -> a.name("media")
                            .getter(Message::getMedia)
                            .setter(Message::setMedia)
                            .attributeConverter(ListAttributeConverter.builder(EnhancedType.listOf(String.class))
                                    .collectionConstructor(ArrayList::new)
                                    .elementConverter(StringAttributeConverter.create())
                                    .build()))
                    .addAttribute(String.class, a -> a.name("updated")
                            .getter(Message::getUpdated)
                            .setter(Message::setUpdated))
                    .build();

    @Autowired
    MessageDynamoDBRepository(final DynamoDbEnhancedClient dynamoDbEnhancedClient, final PaginationTokenSerializer tokenSerializer, final DynamoDbClient dynamoDbClient) {
        this.table = dynamoDbEnhancedClient.table(TABLE_NAME, MESSAGE_STATIC_TABLE_SCHEMA);
        this.client = dynamoDbClient;
        this.tokenSerializer = tokenSerializer;
    }

    /**
     *
     *
     * */
    @Override
    public Message updateMessage(
            final String partitionKey, final String sortKey, final String newText, final String newMedia
    ) {
        // TODO:
        return null;
    }

    /**
     *
     *
     * */
    @Override
    public Message deleteMessage(final Message message) {
        Key key = Key.builder()
                .partitionValue(message.getPk())
                .sortValue(message.getSk())
                .build();
        DeleteItemEnhancedRequest request = DeleteItemEnhancedRequest.builder()
                .key(key)
                .build();

        Message deletedMessage = table.deleteItem(request);
        log.info("Message deleted successfully: \n{}", deletedMessage);
        return deletedMessage;
    }

    /**
     *
     *
     * */
    @Override
    public Message createMessage(final Message message) {
        String pk = message.getChatId();
        // If channelId is given in request, append it to groupId to form partitionKey
        if (message.getChannelId() != null && !message.getChannelId().isEmpty()) {
            pk += "#" + message.getChannelId();
        }

        final String sk = String.format("%s#%s", message.getCreated(), message.getId());

        final Message newMessage = message.toBuilder().pk(pk).sk(sk).build();

        log.info("Creating message: \n{}", newMessage);
        table.putItem(newMessage);
        log.info("Message successfully created: {}", newMessage);
        return newMessage;
    }


    /**
     *
     * */
    @Override
    public MessageList getMessages(final String groupId, final String channelName, final String paginationToken) throws MessagesNotFoundException {

        // Holds attribute values to use in query as aliases in case keys are reserved words
        Map<String,String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#pk","pk");
        // #pk -> pk -> :pkValue -> ACTUAL_KEY!!???
        String partitionKey = String.format("%s#%s", groupId, channelName);
        Map<String,AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":pkValue", AttributeValue.builder().s(partitionKey).build());

        QueryRequest.Builder  queryRequestBuilder = QueryRequest.builder()
                .tableName(TABLE_NAME)
                .keyConditionExpression("#pk = :pkValue")
                .expressionAttributeNames(expressionAttributesNames)
                .expressionAttributeValues(expressionAttributeValues)
                .limit(PAGE_SIZE);

        // Set exclusiveStartKey for next page if present. This indicates more items are left
        //  to query or that this is the initial request to get messages.
        if (paginationToken != null && !paginationToken.isEmpty()) {
            try {
                queryRequestBuilder.exclusiveStartKey(tokenSerializer.deserialize(paginationToken));
            } catch (InvalidTokenException e) {
                log.error("Bad request: unable to deserialize next token {}. Token is invalid.", paginationToken, e);
                throw new MessagesNotFoundException("Bad request: unable to deserialize next token. Token is invalid." + e.getMessage());
            }
        }
        QueryResponse queryResponse = client.query(queryRequestBuilder.build());
        List<Message> messages = new ArrayList<>();

        // Populate messages after mapping them to Message class
        queryResponse.items().forEach(m -> messages.add(table.tableSchema().mapToItem(m)));
        Map<String, AttributeValue> lastEvaluatedKey = queryResponse.lastEvaluatedKey();
        MessageList.MessageListBuilder messageListBuilder = MessageList.builder()
                .messages(messages);

        // Populate pagination token if exists
        if (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty()) {
            messageListBuilder.cursor(tokenSerializer.serialize(lastEvaluatedKey));
        }
        return messageListBuilder.build();
    }

    @Override
    public Message editMessage() {
        return null;
    }

    @Override
    public Message getMessage() {
        return null;
    }

    @Override
    public Message deleteChat() {
        return null;
    }

    @Override
    public Message deleteGroup() {
        return null;
    }

}
