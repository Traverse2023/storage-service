package com.traverse.storage.post;

import com.traverse.storage.models.Post;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/createPost")
    public Post createPost(@RequestBody final String requestBody) {
        // System.out.println("REQUEST BODY: " + requestBody);
        // JSONObject jsonBody = new JSONObject(requestBody);
        // String groupName = jsonBody.getString("groupName");
        // return postService.addGroup(groupName);
    }

    @PostMapping("/getPost")
    public Post getPost(@RequestBody final String requestBody) {
        // System.out.println("REQUEST BODY: " + requestBody);
        // JSONObject jsonBody = new JSONObject(requestBody);
        // String groupName = jsonBody.getString("groupName");
        // return postService.addGroup(groupName);
    }
}
