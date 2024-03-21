package com.traverse.storage.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import java.time.ZonedDateTime;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("pk")}))
    String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("sk")}))
    String sk;

    private String id;

    private NotificationType type;

    private String created;

    private String chatId;

    private String sender;

}
