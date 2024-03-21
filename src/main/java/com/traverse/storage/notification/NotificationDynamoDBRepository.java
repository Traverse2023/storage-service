package com.traverse.storage.notification;

import com.traverse.storage.models.Notification;
import com.traverse.storage.models.NotificationList;
import com.traverse.storage.models.NotificationType;
import com.traverse.storage.utils.exceptions.serializer.InvalidTokenException;
import com.traverse.storage.utils.serializer.PaginationTokenSerializer;
import com.traverse.storage.utils.serializer.TokenSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primarySortKey;

@Slf4j
@Repository
public class NotificationDynamoDBRepository implements NotificationRepository {
    private final DynamoDbClient client;
    private final TokenSerializer<Map<String, AttributeValue>> tokenSerializer;
    private final DynamoDbTable<Notification> table;
    private final String TABLE_NAME = "Notifications";
    private final int PAGE_SIZE = 20;

    static final StaticTableSchema<Notification> NOTIFICATION_STATIC_TABLE_SCHEMA =
            StaticTableSchema.builder(Notification.class)
                    .newItemSupplier(Notification::new)
                    .addAttribute(String.class, a -> a.name("pk")
                            .getter(Notification::getPk)
                            .setter(Notification::setPk)
                            .tags(primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("sk")
                            .getter(Notification::getSk)
                            .setter(Notification::setSk)
                            .tags(primarySortKey()))
                    .addAttribute(String.class, a -> a.name("id")
                            .getter(Notification::getId)
                            .setter(Notification::setId))
                    .addAttribute(NotificationType.class, a -> a.name("type")
                            .getter(Notification::getType)
                            .setter(Notification::setType))
                    .addAttribute(String.class, a -> a.name("created")
                            .getter(Notification::getCreated)
                            .setter(Notification::setCreated))
                    .addAttribute(String.class, a -> a.name("chatId")
                            .getter(Notification::getChatId)
                            .setter(Notification::setChatId))
                    .addAttribute(String.class, a -> a.name("sender")
                            .getter(Notification::getSender)
                            .setter(Notification::setSender))
                    .build();

    @Autowired
    NotificationDynamoDBRepository(final DynamoDbEnhancedClient dynamoDbEnhancedClient, final PaginationTokenSerializer tokenSerializer, final DynamoDbClient dynamoDbClient) {
        this.table = dynamoDbEnhancedClient.table(TABLE_NAME, NOTIFICATION_STATIC_TABLE_SCHEMA);
        this.client = dynamoDbClient;
        this.tokenSerializer = tokenSerializer;
    }


    @Override
    public Notification deleteNotification(Notification notification) {

        Key key = Key.builder()
                .partitionValue(notification.getPk())
                .sortValue(notification.getSk())
                .build();
        DeleteItemEnhancedRequest request = DeleteItemEnhancedRequest.builder()
                .key(key)
                .build();
        final Notification deletedNotification = table.deleteItem(request);
        log.info("Notification deleted successfully: \n{}", deletedNotification);
        return deletedNotification;
    }

    @Override
    public Notification createNotification(Notification notification) {
        final String sk = String.format("%s#%s", notification.getId(), notification.getCreated());
        Notification newNotification = notification.toBuilder().sk(sk).build();
        table.putItem(newNotification);
        log.info("Notification created successfully: \n{}", newNotification);
        return newNotification;
    }

    @Override
    public NotificationList getNotifications(String partitionToken, String paginationToken) {
        Map<String,String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put("#pk","pk");
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":pkValue", AttributeValue.builder().s(partitionToken).build());

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
                throw new RuntimeException("Bad request: unable to deserialize next token. Token is invalid." + e.getMessage());
            }
        }
        QueryResponse queryResponse = client.query(queryRequestBuilder.build());
        List<Notification> notifications = new ArrayList<>();

        queryResponse.items().forEach(n -> notifications.add(table.tableSchema().mapToItem(n)));
        Map<String, AttributeValue> lastEvaluatedKey = queryResponse.lastEvaluatedKey();
        NotificationList.NotificationListBuilder notificationListBuilder = NotificationList.builder()
                .notifications(notifications);
        if (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty()) {
            notificationListBuilder.cursor(tokenSerializer.serialize(lastEvaluatedKey));
        } else {
            log.info("No pagination token present. End of notifications.");
        }
        log.info("Notifications retrieved successfully: \n{}", notifications);
        return notificationListBuilder.build();
    }

}

