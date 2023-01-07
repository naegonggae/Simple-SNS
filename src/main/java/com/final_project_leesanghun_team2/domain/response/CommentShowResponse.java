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
public class CommentShowResponse {
    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private LocalDateTime createdAt;

    public static CommentShowResponse of(Comment comment){
        return new CommentShowResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getUserName(),
                comment.getPost().getId(),
                comment.getCreatedAt()
        );
    }

    public static Page<CommentShowResponse> toList(Page<Comment> comments){
        Page<CommentShowResponse> commentShowResponsePage = comments.map(m -> CommentShowResponse.builder() // 이게 뭐야...
                .id(m.getId())
                .comment(m.getComment())
                .userName(m.getUser().getUserName())
                .postId(m.getPost().getId())
                .createdAt(m.getCreatedAt())
                .build());
        return commentShowResponsePage;
    }


    /*


    public static CommentShowResponse fromComment(Comment comment){
        return new CommentShowResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getUserName(),
                comment.getPost().getId(),
                comment.getCreatedAt()
                //comment.getLastModifiedAt()
        );
    }

    public static Page<CommentShowResponse> toCommentResponse(Page<Comment> comments) {
        Page<CommentShowResponse> toCommentResponse = comments.map(m -> CommentShowResponse.builder()
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
