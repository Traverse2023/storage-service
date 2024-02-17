package com.traverse.storage.unit;

import com.traverse.storage.group.GroupRepository;
import com.traverse.storage.group.GroupService;
import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import com.traverse.storage.utils.exceptions.GroupCreationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepositoryMock;
    @InjectMocks
    private GroupService groupService;
    private final String groupName = "groupName";
    private Group group;

    @BeforeEach
    void init() {
        List<Message> messageList = new ArrayList<>();
        Channel channel = Channel.builder()
                .name("general")
                .messageCount(1)
                .messages(messageList)
                .build();
        Map<String, Channel> channels = new HashMap<>();
        channels.put("general", channel);

        group = Group.builder()
                .id("123456789")
                .name(groupName)
                .channels(channels)
                .build();
    }

    @Test
    public void addGroupReturnsNewGroup() {

        when(groupRepositoryMock.insert(Mockito.any(Group.class))).thenReturn(group);

        final Group createdGroup = groupService.addGroup(groupName);

        assertNotNull(createdGroup);
        assertEquals(createdGroup, group);
    }

    @Test
    public void whenExceptionCaughtThrowsGroupCreationException() {

        when(groupRepositoryMock.insert(Mockito.any(Group.class))).thenThrow(new RuntimeException("exception"));

        assertThrows(GroupCreationException.class, () -> groupService.addGroup(groupName));

    }
}
