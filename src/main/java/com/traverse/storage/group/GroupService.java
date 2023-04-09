package com.traverse.storage.group;

import com.traverse.storage.group.models.Channel;
import com.traverse.storage.group.models.Group;
import com.traverse.storage.group.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getGroups() {
        Message msg = new Message("isfar", "Hey");
        ArrayList<Message> msgs = new ArrayList<Message>();
        msgs.add(msg);
        Channel channel = new Channel("general", msgs);
        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(channel);
        Group group = new Group(channels);

        groupRepository.insert(group);
        return List.of(
                group
        );
    }
}
