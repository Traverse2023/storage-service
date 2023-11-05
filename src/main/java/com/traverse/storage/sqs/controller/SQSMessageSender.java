package com.traverse.storage.sqs.controller;

import com.traverse.storage.group.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SQSMessageSender {

    private final QueueMessagingTemplate template;
    @Value("${cloud.aws.end_point.uri}")
    private String QUEUE_URL;


    @Autowired
    public SQSMessageSender(QueueMessagingTemplate template) {
        this.template = template;
    }

    public void sendMessage(Message message) {
        log.info("Sending message to SQS. \n Message: ${message}");
        try {
            template.convertAndSend("", message);
        } catch(MessagingException e) {
            log.error("An error occurred when sending a message to SQS:\n${e.getMessage}");
        }
        log.info("Message successfully sent to SQS.");
    }


}
