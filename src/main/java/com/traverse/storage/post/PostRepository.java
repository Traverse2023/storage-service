package com.traverse.storage.post;

import com.traverse.storage.models.Post;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface PostRepository extends MongoRepository<Post, String> {

    @Query(value = "{ '_id' : ?0}", fields = "{ postID, userID, postName, content, upvotes, downvotes, comments }")
    Post getPost(String postID);
} 
