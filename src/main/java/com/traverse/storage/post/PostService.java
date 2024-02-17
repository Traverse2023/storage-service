package com.traverse.storage.post;


import com.traverse.storage.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        // TODO: Exception handling

    }

    public Post getPost(Post post) {
        // TODO: Exception handling

    }

}
