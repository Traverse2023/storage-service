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

        expressionAttributeNames.put("#pk", "pk");
        expressionAttributeNames.put("#sk", "sk");
        //expressionAttributeNames.put("#level", "level");
        //expressionAttributeNames.put("#problemId", "problemId");
        expressionAttributeNames.put("#tags", "tags");
        expressionAttributeNames.put("#misc", "misc");
        expressionAttributeNames.put("#name", "name");

        // Should ProblemID = sk+pk   1+easy
        // ProblemID = sk
        // is problem id sort key or is problem id the combination or a separate field entirely
        // http://traverse/searchProblem?ProblemID=3838#E
        // http://traverse/searchProblem?ProblemID=3838#M
        // http://traverse/searchProblem?ProblemID=3838#H

        //which partition? and what is the sort key

        // ProblemId Problem1 | sk  = 1 | pk = level = easy
        // ProblemId Problem2 | sk  = 1 | pk = level = medium
        // ProblemID Problem3 | sk  = 2 | pk = level = easy
        // ProblemID Problem4 | sk  = 2 | pk = level = medium
        // ProblemID Problem5 | sk  = 1 | pk = level = hard


        //pk = part k  +  sort key
        // fields { #pk-> pk }
        // values {:pk -> 12312341234}
        // Add partition key condition

        expressionAttributeValues.put(":pk", AttributeValue.builder().s(level).build());
        expressionAttributeValues.put(":sk", AttributeValue.builder().s(probSk).build());
        //expressionAttributeValues.put(":level", AttributeValue.builder().s(level).build());
        //expressionAttributeValues.put(":problemId", AttributeValue.builder().s(problemId).build());
        expressionAttributeValues.put(":name", AttributeValue.builder().s(name).build());
        expressionAttributeValues.put(":tags", AttributeValue.builder().ss(tags).build());
        expressionAttributeValues.put(":misc", AttributeValue.builder().s(misc).build());
        // add second partition key condition

        StringBuilder keyConditionExpression = new StringBuilder();

        // name , level, probSk
        // Include the partition key condition if it's provided
        if (level != null && !level.isEmpty()) {
            expressionAttributeNames.put("#pk", "pk");
            expressionAttributeValues.put(":pk", AttributeValue.builder().s(level).build());
            keyConditionExpression.append("#pk = :pk");
        }

        // Include the sort key condition if it's provided
        if (probSk != null && !probSk.isEmpty()) {
            if (!keyConditionExpression.isEmpty()) {
                keyConditionExpression.append(" AND ");
            }
            expressionAttributeNames.put("#sk", "sk");
            expressionAttributeValues.put(":sk", AttributeValue.builder().s(probSk).build());
            keyConditionExpression.append("#sk = :sk");
        }

        // Validate if at least one key condition is specified
        if (keyConditionExpression.isEmpty()) {
            throw new ProblemNotFoundException("At least one of 'pk' or 'sk' must be provided.");
        }

        // Build the QueryRequest
        QueryRequest.Builder queryRequestBuilder = QueryRequest.builder()
                .tableName(TABLE_NAME)
                .keyConditionExpression(keyConditionExpression.toString())
                .expressionAttributeNames(expressionAttributeNames)
                .expressionAttributeValues(expressionAttributeValues)
                .limit(PAGE_SIZE);

        // Set exclusiveStartKey if pagination token is provided
        if (paginationToken != null && !paginationToken.isEmpty()) {
            try {
                queryRequestBuilder.exclusiveStartKey(tokenSerializer.deserialize(paginationToken));
            } catch (InvalidTokenException e) {
                log.error("Bad request: unable to deserialize next token {}. Token is invalid.", paginationToken, e);
                throw new ProblemNotFoundException("Bad request: unable to deserialize next token. Token is invalid." + e.getMessage());
            }
        }


        // NOTE: Build the query request FINALLY
        QueryRequest queryRequest = queryRequestBuilder.build();

        // Execute the query
        QueryResponse queryResponse = client.query(queryRequest);

        List<Problem> problems = new ArrayList<>();

        // Populate problems after mapping them to Message class
        queryResponse.items().forEach(m -> problems.add(table.tableSchema().mapToItem(m)));
        Map<String, AttributeValue> lastEvaluatedKey = queryResponse.lastEvaluatedKey();

        // Build the response
        ProblemsList.ProblemsListBuilder problemsListBuilder = ProblemsList.builder()
                .problems(problems);

        if (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty()) {
            problemsListBuilder.cursor(tokenSerializer.serialize(lastEvaluatedKey));
        }

        // If no problems found, throw an exception
        if (problems.isEmpty()) {
            throw new ProblemNotFoundException("No problems found with the specified criteria.");
        }

        // Handle the response and return results
        return problemsListBuilder.build();
    }
}