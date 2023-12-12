package com.traverse.storage.sqs;

import com.traverse.storage.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        log.info("Sending message to SQS. \n Message: {}", message);
        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("contentType", "application/json");
            headers.put("message-group-id", "groupID");
            headers.put("message-deduplication-id", "messageDeduplicationId");

            template.convertAndSend(QUEUE_URL, message, headers);
        } catch(MessagingException e) {
            log.error("An error occurred when sending a message to SQS:\n{}", e.getMessage());
        } catch(Exception e) {
            log.error("An unknown error occurred when sending message to SQS {}", e.getMessage());
        }
        log.info("Message successfully sent to SQS.");
    }


}
