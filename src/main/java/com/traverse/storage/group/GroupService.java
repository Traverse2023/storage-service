package com.traverse.storage.group;


import com.traverse.storage.models.Group;
import com.traverse.storage.utils.exceptions.GroupCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group addGroup(final String name) throws GroupCreationException {
        try {
            return groupRepository.insert(new Group(name));
        } catch (Exception e) {
            throw new GroupCreationException(e.getMessage());
        }
    }
}
