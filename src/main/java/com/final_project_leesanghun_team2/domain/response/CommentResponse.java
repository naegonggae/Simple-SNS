package com.final_project_leesanghun_team2.domain.response;

import com.final_project_leesanghun_team2.domain.entity.Comment;
import com.final_project_leesanghun_team2.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public static CommentResponse fromComment(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .userName(comment.getUser().getUserName())
                .postId(comment.getPost().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }


    /*


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

    public static Page<CommentResponse> toCommentResponse(Page<Comment> comments) {
        Page<CommentResponse> toCommentResponse = comments.map(m -> CommentResponse.builder()
                .id(m.getId())
                .comment(m.getComment())
                .userName(m.getUser().getUserName())
                .postId(m.getPost().getId())
                .createdAt(m.getCreatedAt())
                .build());
        return toCommentResponse;
    }

 */
}
