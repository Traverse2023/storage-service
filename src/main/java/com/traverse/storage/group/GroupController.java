package com.traverse.storage.group;

import com.traverse.storage.group.models.Group;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/all")
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @PostMapping("/createGroup")
    public Group createGroup(@RequestBody String requestBody) {
        System.out.println("REQUEST BODY: " + requestBody);
        JSONObject jsonBody = new JSONObject(requestBody);
        String name = jsonBody.getString("name");
        return groupService.addGroup(name);
    }
}
