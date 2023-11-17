package com.traverse.storage.group;

import com.traverse.storage.group.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @PostMapping("/createGroup")
    public void createGroup(@RequestBody String requestBody) {
        System.out.println("REQUEST BODY: " + requestBody);
        String name = requestBody.split("=")[1];
//        groupService.createGroup(name);
    }
}
