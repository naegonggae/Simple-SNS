package com.final_project_leesanghun_team2.domain.response;

import com.final_project_leesanghun_team2.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponse {
    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private LocalDateTime createdAt;
    //private LocalDateTime lastModifiedAt;
/*
    public static CommentResponse fromEntity(Comment entity) {
        return new CommentResponse(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getCreatedAt()
        );
    }

 */

    public static CommentResponse fromComment(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getUserName(),
                comment.getPost().getId(),
                comment.getCreatedAt()
                //comment.getLastModifiedAt()
        );
    }
}
