package com.traverse.storage.group;


import com.traverse.storage.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private GroupMongoDBRepository groupMongoDBRepository;

    public Group addGroup(final String name) {
        return groupMongoDBRepository.insert(new Group(name));
        // TODO: exception handling
    }
}
