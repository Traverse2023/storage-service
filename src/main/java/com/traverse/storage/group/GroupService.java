package com.traverse.storage.group;

import com.traverse.storage.group.models.Channel;
import com.traverse.storage.group.models.Group;
import com.traverse.storage.group.models.Message;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getGroups() {
        Message msg = new Message("isfar", "Hey");
        ArrayList<Message> msgs = new ArrayList<Message>();
        msgs.add(msg);
        Channel channel = new Channel("general", msgs);
        ArrayList<Channel> channels = new ArrayList<Channel>();
        channels.add(channel);
        Group group = new Group(channels);

        this.groupRepository.insert(group);
        return List.of(
                group
        );
    }
}
