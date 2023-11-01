package com.traverse.storage;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;

@SpringBootApplication

public class StorageApplication {
    Logger logger= LoggerFactory.getLogger(StorageApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

    @SqsListener("https://sqs.us-east-1.amazonaws.com/616401808986/FarhanQueue")
    public void receiveStringMessage(final String message) {
        logger.info("message received {}", message);
    }

}
