package com.traverse.storage.post;

import com.traverse.storage.models.Post;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/createPost")
    public Post createPost(@RequestBody final String requestBody) {
        // System.out.println("REQUEST BODY: " + requestBody);
        // JSONObject jsonBody = new JSONObject(requestBody);
        // String groupName = jsonBody.getString("groupName");
        // return postService.addGroup(groupName);
    }

    @GetMapping("/getPost")
    public Post getPost(@RequestBody final String requestBody) {
        return postRepository.getPost(requestBody);
    }
}
