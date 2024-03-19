package com.traverse.storage.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

@Configuration
public class DynamoDBConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.dynamodb.endpoint:#{null}}")
    private String endpoint;



    @Bean
    public DynamoDbClient getDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(awsRegion))
                .endpointOverride(URI.create(endpoint!=null ? endpoint : String.format("dynamodb.%s.amazonaws.com", awsRegion)))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

}
