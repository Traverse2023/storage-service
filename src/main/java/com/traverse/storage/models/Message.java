package com.traverse.storage.models;



import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.ZonedDateTime;
import java.util.List;





@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Message {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("pk")}))
    String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("sk")}))
    String sk;

    String id;

    MessageType type;

    ZonedDateTime created;

    String sender;

    String text;

    List<String> media;

    ZonedDateTime updated;

}
