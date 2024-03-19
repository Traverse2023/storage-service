package com.traverse.storage.utils.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;

@Configuration
public class AwsKmsConfig {
    @Value("${aws.region}")
    private String awsRegion;
    @Bean
    public  KmsClient amazonKmsClient() {
        return KmsClient.builder()
                .region(Region.of(awsRegion.toUpperCase()))
                .build();
    }
}