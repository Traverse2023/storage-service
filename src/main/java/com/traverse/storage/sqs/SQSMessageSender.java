//package com.traverse.storage.sqs;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import com.traverse.storage.models.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
//import org.springframework.messaging.MessagingException;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestBody;
//
//
//import java.util.Random;
//
//@Component
//@Slf4j
//public class SQSMessageSender {
//    private final QueueMessagingTemplate template;
//    private final ObjectMapper mapper;
//    @Value("${cloud.aws.end_point.uri}")
//    private String QUEUE_URL;
//
//    @Autowired
//    public SQSMessageSender(QueueMessagingTemplate template, ObjectMapper mapper) {
//        this.mapper = mapper;
//        this.template = template;
//    }
//
//
//    public void sendMessage(@RequestBody Message message) {
//        log.info("Sending message to SQS. \n Message: {}", message.getText());
//        try {
//            Random rand = new Random();
//            String  jsonString = mapper.writeValueAsString(message);
//            log.info("Sending message: {}", jsonString);
//            template.send(QUEUE_URL, MessageBuilder
//                    .withPayload(jsonString)
//                    .setHeader("contentType", "application/json")
//                    .setHeader("message-group-id", "groupID")
//                    .setHeader("message-deduplication-id", Integer.toHexString(rand.nextInt()))
//                    .build());
//            log.info("Message sent successfully: " + message.getText());
//        } catch(MessagingException e) {
//            log.error("An error occurred when sending a message to SQS: {}", e.getMessage());
//        } catch(Exception e) {
//            log.error("An unknown error occurred when sending message to SQS: {}", e.getMessage());
//        }
//    }
//
//
//}
//
