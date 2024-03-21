package com.traverse.storage.models;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.ZonedDateTime;
import java.util.List;





@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("pk")}))
    String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("sk")}))
    String sk;

    String id;

    String chatId;

    String channelId;

    MessageType type;

    String created;

    String sender;

    String text;

    List<String> media;

    String updated;

}
