package com.traverse.storage.message;

import com.traverse.storage.models.Message;
import com.traverse.storage.models.MessageList;
import com.traverse.storage.models.MessageType;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping(path = "api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     *
     */
    @GetMapping("/{groupId}/{channelName}")
    public MessageList getMessages(@PathVariable String groupId, @PathVariable String channelName, @RequestBody(required = false) String requestBody) throws MessagesNotFoundException {
        log.info("Getting messages...");
        JSONObject jsonBody = new JSONObject(requestBody!= null ? requestBody : "{}");
        return messageService.getMessages(groupId, channelName, jsonBody.optString("cursor"));
    }

    /**
     *
     */
    @PostMapping("/addMessage")
    public Message createMessage(@RequestBody Message message) {
        log.info("Creating message:\n{}", message);
        return messageService.createMessage(message);

    }

    /**
     *
     */
    @DeleteMapping("/deleteMessage")
    public Message deleteMessage(@RequestBody Message message){
        Message deletedMessage = messageService.deleteMessage(message);
        log.info("Deleted message: \n{}", deletedMessage);
        return deletedMessage;
    }

    /**
     *
     */
//    @GetMapping
//    public Message getMessage(final String pk, final String sk){
//        return messageService.getMessage();
//    }
//
//    /**
//     *
//     */
//    @PatchMapping
//    public Message editMessage(final String pk, final String sk) {
//        return messageService.editMessage();
//    }
//
//    /**
//     *
//     */
//    @DeleteMapping
//    public void deleteChat(final String pk) {
//        messageService.deleteChat();
//    }
//
//    /**
//     *
//     */
//    @DeleteMapping
//    public void deleteGroup(final String groupId){
//        messageService.deleteGroup();
//    }
}
