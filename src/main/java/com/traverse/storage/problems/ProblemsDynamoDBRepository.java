package com.traverse.storage.problems;
import com.traverse.storage.models.*;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;
import com.traverse.storage.utils.exceptions.mongo.ProblemNotFoundException;
import com.traverse.storage.utils.exceptions.serializer.InvalidTokenException;
import com.traverse.storage.utils.serializer.PaginationTokenSerializer;
import com.traverse.storage.utils.serializer.TokenSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.ListAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.StringAttributeConverter;
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
public class ProblemsDynamoDBRepository implements ProblemsRepository {
    private final DynamoDbClient client;
    private final TokenSerializer<Map<String, AttributeValue>> tokenSerializer;
    private final DynamoDbTable<Problem> table;
    private final String TABLE_NAME = "Problems";
    private final int PAGE_SIZE = 20;
    static final StaticTableSchema<Problem> PROBLEM_STATIC_TABLE_SCHEMA =
            StaticTableSchema.builder(Problem.class)
                    .newItemSupplier(Problem::new)
                    .addAttribute(String.class, a -> a.name("pk")
                            .getter(Problem::getPk)
                            .setter(Problem::setPk)
                            .tags(primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("sk")
                            .getter(Problem::getSk)
                            .setter(Problem::setSk)
                            .tags(primarySortKey()))
                    .addAttribute(String.class, a -> a.name("problemId")
                            .getter(Problem::getProblemId)
                            .setter(Problem::setProblemId))
                    .addAttribute(ProblemLevel.class, a -> a.name("level")
                            .getter(Problem::getLevel)
                            .setter(Problem::setLevel))
                    .addAttribute(String.class, a -> a.name("created")
                            .getter(Problem::getCreated)
                            .setter(Problem::setCreated))
                    .addAttribute(String.class, a -> a.name("text")
                            .getter(Problem::getText)
                            .setter(Problem::setText))
                    .addAttribute(String.class, a -> a.name("problemName")
                            .getter(Problem::getProblemName)
                            .setter(Problem::setProblemName))
                    .addAttribute(EnhancedType.listOf(String.class), a -> a.name("tags")
                            .getter(Problem::getTags)
                            .setter(Problem::setTags)
                            .attributeConverter(ListAttributeConverter.builder(EnhancedType.listOf(String.class))
                                    .collectionConstructor(ArrayList::new)
                                    .elementConverter(StringAttributeConverter.create())
                                    .build()))
                    .build();


    @Autowired
    ProblemsDynamoDBRepository(final DynamoDbEnhancedClient dynamoDbEnhancedClient, final PaginationTokenSerializer tokenSerializer, final DynamoDbClient dynamoDbClient) {
        this.table = dynamoDbEnhancedClient.table(TABLE_NAME, PROBLEM_STATIC_TABLE_SCHEMA);
        this.client = dynamoDbClient;
        this.tokenSerializer = tokenSerializer;
    }

    @Override
    public ProblemsList getProblemsList(String problemId, String level, List<String> tags, String misc, String paginationToken) throws ProblemNotFoundException {
        // Build query conditions
        Map<String, String> expressionAttributeNames = new HashMap<>();
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();

        expressionAttributeNames.put("#level", "level");

        //pk = part k  +  sort key

        expressionAttributeValues.put(":name", AttributeValue.builder().s(problemId).build());

        StringBuilder keyConditionExpression = new StringBuilder("pk = :name");

        // Add sort key condition for problemId
        if(problemId != null && !problemId.isEmpty()){
            keyConditionExpression.append("and #problemId = :problemId");
            expressionAttributeValues.put(":problemId", AttributeValue.builder().s(problemId).build());
        }
        // Add sort key condition for level
        if(level != null && !level.isEmpty()){
            keyConditionExpression.append("and #level = :level");
            expressionAttributeValues.put(":level", AttributeValue.builder().s(problemId).build());
        }
        //GSI Global search index...

        // Build the QueryRequest
        QueryRequest.Builder queryRequestBuilder = QueryRequest.builder()
                .tableName(TABLE_NAME)
                .keyConditionExpression(keyConditionExpression.toString())
                .expressionAttributeNames(expressionAttributeNames)
                .expressionAttributeValues(expressionAttributeValues)
                .limit(PAGE_SIZE);
        QueryRequest queryRequest = queryRequestBuilder.build();

        // Execute the query
        QueryResponse queryResponse = client.query(queryRequest);

        // Handle the response and return results
        List<Problem> problems = new ArrayList<>();
        for (Map<String, AttributeValue> item : queryResponse.items()) {
            Problem problem = table.mapToItem(item);
            problems.add(problem);
        }

        if (problems.isEmpty()) {
            throw new ProblemNotFoundException("No problems found with the specified criteria.");
        }

        // Serialize the pagination token for the next page, if available
        String nextToken = queryResponse.hasLastEvaluatedKey() ? tokenSerializer.serialize(queryResponse.lastEvaluatedKey()) : null;

        return new ProblemsList(problemId, nextToken);
    }
}