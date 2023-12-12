package com.traverse.storage.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.traverse.storage.models.Message;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping()
    public List<Message> getMessages() {
    return messageService.getMessages();
    }
}
