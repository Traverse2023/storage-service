package com.traverse.storage.message;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.*;
import com.traverse.storage.models.Message;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@EnableSqs
@RequestMapping(path = "api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @GetMapping("/{groupId}/{channelName}/{pageNumber}")
    public List<Message> getMessages(@PathVariable String groupId, @PathVariable String channelName, @PathVariable int pageNumber) {
        log.info("Getting messages...");
        return messageService.getMessages(groupId, channelName, pageNumber);
    }

//    @SqsListener(value = "${cloud.aws.end_point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
//    public void receiveMessageFromQueue(Message message) {
//        log.info("Receiving message from SQS...\n{}", message.toString());
//        messageService.saveMessage(message);
//    }
    @PostMapping("/addMessage")
    public void receiveMessageFromQueue(@RequestBody String requestBody) {
        JSONObject jsonBody = new JSONObject(requestBody);
        log.info("Receiving message from Main Service...\n{}", jsonBody.toString());
        Message message = new Message();
        message.setEmail(jsonBody.getString("email"));
        message.setText(jsonBody.getString("text"));
        message.setFirstName(jsonBody.getString("firstName"));
        message.setLastName(jsonBody.getString("lastName"));
//        message.setTime(LocalDateTime.parse(jsonBody.getString("time")));
        message.setGroupId(jsonBody.getString("groupId"));
        message.setChannelName(jsonBody.getString("channelName"));
        messageService.saveMessage(message);
    }
}
