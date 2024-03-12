package com.traverse.storage.dynamoModels;



import com.traverse.storage.models.MessageType;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.ZonedDateTime;
import java.util.List;


@Value
@Builder
@DynamoDbImmutable(builder = Message.MessageBuilder.class)
public class Message {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("pk")}))
    String partitionKey;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("sk")}))
    String sortKey;

    @Getter(onMethod = @__({@DynamoDbAttribute("id")}))
    String id;

    @Getter(onMethod = @__({@DynamoDbAttribute("type")}))
    MessageType type;

    @Getter(onMethod = @__({@DynamoDbAttribute("created")}))
    ZonedDateTime created;

    @Getter(onMethod = @__({@DynamoDbAttribute("sender")}))
    String sender;

    @Getter(onMethod = @__({@DynamoDbAttribute("text")}))
    String text;

    @Getter(onMethod = @__({@DynamoDbAttribute("media")}))
    List<String> mediaURLs;

    @Getter(onMethod = @__({@DynamoDbAttribute("updated")}))
    ZonedDateTime updated;

}
