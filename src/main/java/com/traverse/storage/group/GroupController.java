package com.traverse.storage.group;

import com.traverse.storage.models.Group;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/groups")
public final class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/createGroup")
    public Group createGroup(@RequestBody final String requestBody) {
        System.out.println("REQUEST BODY: " + requestBody);
        JSONObject jsonBody = new JSONObject(requestBody);
        String groupName = jsonBody.getString("groupName");
        return groupService.addGroup(groupName);
    }
}
