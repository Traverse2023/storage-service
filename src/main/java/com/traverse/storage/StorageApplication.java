package com.traverse.storage;

import com.traverse.storage.models.ChatMessageType;
import com.traverse.storage.models.Message;
import com.traverse.storage.sqs.SQSMessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication

public class StorageApplication {
    Logger logger= LoggerFactory.getLogger(StorageApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
