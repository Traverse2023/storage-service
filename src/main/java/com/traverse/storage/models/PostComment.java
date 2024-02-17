package com.traverse.storage.models;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostComment {
    @Id
    private String commentID;
    private String userID;
    private String content;
    private List<CommentReply> replies;
}