package com.traverse.storage.utils.config;

<<<<<<< HEAD
import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
=======
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
>>>>>>> 6a080f995d200764ff6136811d47487689e8b03f
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

<<<<<<< HEAD
    @Value("${aws.dynamodb.endpoint}")
    private String dynamodbEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.dynamodb.secretKey}")
    private String dynamodbSecretKey;

    @Value("${aws.dynamodb.accessKey}")
    private String dynamodbAccessKey;

    @Value("${aws.dynamodb.sessionToken}")
    private String dynamodbSessionToken;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBAsyncClientBuilder
=======
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
>>>>>>> 6a080f995d200764ff6136811d47487689e8b03f
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                dynamodbEndpoint,
                                awsRegion
                        )
<<<<<<< HEAD
                )
                .withCredentials(amazonDynamoDBCredentials())
                .build();
    }

    private AWSCredentialsProvider amazonDynamoDBCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(dynamodbAccessKey, dynamodbSecretKey));
    }
=======
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
    //}
>>>>>>> 6a080f995d200764ff6136811d47487689e8b03f

}
