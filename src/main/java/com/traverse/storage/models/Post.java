package com.traverse.storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String postName;
    private String content;
    private int upvotes;
    private int downvotes;
    private List<Comment> comments;
}
