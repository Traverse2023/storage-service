package com.traverse.storage.group;


import com.traverse.storage.models.Channel;
import com.traverse.storage.models.Group;
import com.traverse.storage.models.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getGroups() {
       // Message msg =
        ArrayList<Message> messages = new ArrayList<>();
        Channel channel = new Channel("general", messages);
        ArrayList<Channel> channels = new ArrayList<>();
        channels.add(channel);
        Group group = new Group(channels);

        groupRepository.insert(group);
        return List.of(
                group
        );
    }
}
