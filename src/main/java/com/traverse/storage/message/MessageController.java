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

    @DeleteMapping("/deleteMessage")
    public String deleteMessage(){
        return null;
    }

    public String getMessage(){
        return null;
    }



    @GetMapping("/{groupId}/{channelName}")
    public MessageList getMessages(@PathVariable String groupId, @PathVariable String channelName, @RequestBody(required = false) String requestBody) throws MessagesNotFoundException {
        log.info("Getting messages...");
        JSONObject jsonBody = new JSONObject(requestBody!= null ? requestBody : "{}");
        return messageService.getMessages(groupId, channelName, jsonBody.optString("cursor"));
    }

    @PostMapping("/addMessage")
    public Message createMessage(@RequestBody String requestBody) {
        JSONObject jsonBody = new JSONObject(requestBody);
        log.info("Receiving message from Main Service...\n{}", jsonBody);
        // Convert JSON array to array of media URL strings
        List<String> mediaURLs = new ArrayList<>();
        JSONArray jsonMedia = jsonBody.getJSONArray("media");
        for(int i=0;i<jsonMedia.length();i++) {
            mediaURLs.add(jsonMedia.getString(i));
        }
        return messageService.saveMessage(
                jsonBody.getString("groupId"),
                jsonBody.getString("channelName"),
                MessageType.fromString(jsonBody.getString("type")),
                UUID.randomUUID().toString(),
                ZonedDateTime.now(),
                jsonBody.getString("email"),
                jsonBody.getString("text"),
                mediaURLs
        );

    }
}
