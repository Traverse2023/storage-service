package com.traverse.storage.unit;

import com.traverse.storage.group.GroupRepository;
import com.traverse.storage.message.MessageService;
import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import com.traverse.storage.models.MessagesResponse;
import com.traverse.storage.utils.exceptions.MessageCreationException;
import com.traverse.storage.utils.exceptions.MessagesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private GroupRepository repository;
    private List<Group> groups;
    private Message message;
    private List<Message> messages;
    private final String groupId = "groupId";
    private final String channelName = "general";
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void init(){
        messages = new ArrayList<>();
        message = Message.builder()
                .id("id")
                .email("email@.com")
                .time(LocalDateTime.now())
                .text("text")
                .channelName(channelName)
                .pfpURL("")
                .groupId(groupId)
                .firstName("first")
                .lastName("last")
                .build();
        messages.add(message);
        Channel channel = Channel.builder()
                .messageCount(1)
                .messages(messages)
                .name(channelName)
                .build();
        Map<String, Channel> channels = new HashMap<>();
        channels.put("general", channel);
        Group group = Group.builder()
                .name("name")
                .id(groupId)
                .channels(channels)
                .build();
        groups = new ArrayList<>();
        groups.add(group);
    }

    @Test
    public void saveMessageReturnValidMessage() {

    }

    @Test
    public void saveMessageCatchExceptionReturnsMessageCreationException() {
        when(repository.findById(message.getGroupId())).thenThrow(RuntimeException.class);
        assertThrows(MessageCreationException.class, ()-> messageService.saveMessage(message));
    }

    @Test
    public void getMessagesReturnsGroupMessages() {
        when(repository.findGroupMessages(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(groups);
        MessagesResponse res = messageService.getMessages(groupId, channelName, 1);
        assertNotNull(res.getMessages());
        assertEquals(res.getMessages().get(0), messages.get(0));
        assertEquals(res.getMessageCount(), 1);
    }

    @Test void getMessagesExceptionThrowsMessagesNotFoundException() {
        when(repository.findGroupMessages(groupId, channelName, 0, 1)).thenThrow(RuntimeException.class);
        assertThrows(MessagesNotFoundException.class, ()-> messageService.getMessages(groupId, channelName, 1));
    }
}
