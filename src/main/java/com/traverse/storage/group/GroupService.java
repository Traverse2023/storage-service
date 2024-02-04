package com.traverse.storage.group;


import com.traverse.storage.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public Group addGroup(final String name) {
        return groupRepository.insert(new Group(name));
        // TODO: exception handling
    }
}
