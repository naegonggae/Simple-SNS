package com.final_project_leesanghun_team2.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comment", indexes = {
        @Index(name = "post_id_idx", columnList = "post_id") // 이건 뭘까?
})
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

    public static Comment of(String comment, User user, Post post) {
        Comment entity = new Comment();
        entity.setComment(comment);
        entity.setUser(user);
        entity.setPost(post);
        return entity;
    }
}
