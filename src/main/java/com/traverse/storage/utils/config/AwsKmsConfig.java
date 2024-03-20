package com.traverse.storage.utils.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;

import java.net.URI;

@Configuration
public class AwsKmsConfig {
    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.local_endpoint:#{null}}")
    private String endpoint;

    @Bean
    public  KmsClient amazonKmsClient() {
        return KmsClient.builder()
                .region(Region.of(awsRegion))
                .endpointOverride(URI.create(endpoint!=null ? endpoint : String.format("kms-fips.%s.amazonaws.com", awsRegion)))
                .build();
    }
}
