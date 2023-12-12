package com.traverse.storage;

import com.traverse.storage.models.ChatMessageType;
import com.traverse.storage.models.Message;
import com.traverse.storage.sqs.SQSMessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;

@SpringBootApplication

public class StorageApplication {
    Logger logger= LoggerFactory.getLogger(StorageApplication.class);

    public StorageApplication(SQSMessageSender sender) {
        sender.sendMessage(Message.builder()
                .message("This is bryan's message.")
                .email("briplomo@gmail.com")
                .attachment(new byte[0])
                .type(ChatMessageType.CREATE)
                .groupId("12345")
                .channelName("general")
                .author("Bryan")
                .build()
        );
    }
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
