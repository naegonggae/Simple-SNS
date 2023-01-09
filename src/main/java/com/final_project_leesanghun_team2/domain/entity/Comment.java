package com.final_project_leesanghun_team2.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "comment", indexes = {
        @Index(name = "post_id_idx", columnList = "post_id") // 이건 뭘까?
})
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime deleteAt;

    // 댓글 등록할때 저장정보 포장
    public static Comment of(String comment, User user, Post post) {
        return new Comment(
                comment,
                user,
                post
        );
    }

    // 위에꺼 하려고 생성
    public Comment(String comment, User user, Post post) {
        this.comment = comment;
        this.user = user;
        this.post = post;
    }
}
