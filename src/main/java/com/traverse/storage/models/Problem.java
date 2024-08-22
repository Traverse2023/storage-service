package com.traverse.storage.models;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("pk")}))
    String pk;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("sk")}))
    String sk;

    String problemId;

    ProblemLevel level;

    String created;

    String text;

    String problemName;

    List<String> tags = new ArrayList<>(); // for the added attributes...

}