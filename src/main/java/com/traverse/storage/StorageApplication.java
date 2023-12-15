package com.traverse.storage;

//import com.traverse.storage.group.models.Message;
//import com.traverse.storage.sqs.controller.SQSMessageSender;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;

@SpringBootApplication

public class StorageApplication {
    Logger logger= LoggerFactory.getLogger(StorageApplication.class);

//    public StorageApplication(SQSMessageSender sender) {
//        sender.sendMessage(new Message("Bryan Author", "Bryan's message sent."));
//    }
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
