package com.traverse.storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    private String postID;
    private String userID;
    private String postName;
    private String content;
    private int upvotes;
    private int downvotes;
    private List<PostComment> comments;
}
