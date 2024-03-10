package com.traverse.storage.utils.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

//    @Value("${aws.dynamodb.endpoint}")
//    private String dynamodbEndpoint;

//    @Value("${aws.region}")
//    private String awsRegion;

 //   @Value("${aws.dynamodb.accessKey}")
 //   private String dynamodbAccessKey;

  //  @Value("${aws.dynamodb.secretKey}")
 //   private String dynamodbSecretKey;

//    @Value("${aws.dynamo.sessionToken}")
//    private String dynamodbSessionToken;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB());
    }

    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                dynamodbEndpoint,
                                awsRegion
                        )
                 )
                .build();
    }

    //private AWSCredentialsProvider amazonCredentialProvider() {
   //     return new AWSStaticCredentialsProvider(
     //           new BasicAWSCredentials(
      //                  dynamodbAccessKey,
      //                  dynamodbSecretKey
        //        )
     //   );
    }

}
