package com.final_project_leesanghun_team2.domain.entity;

import com.final_project_leesanghun_team2.domain.request.PostModifyRequest;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Likes> likes;

    // 포스트 등록할때 필요정보 DB에 저장
    public static Post of(String title, String body, User user) {
        return new Post(
                title,
                body,
                user
        );
    }

    // 위에꺼 쓰려고 만듬
    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }
/*
    public static Post of(String title, String body) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        return post;
    }

 */
    /* 포스트 수정할때도 이렇게 메소드 사용하고 싶었는데 못하겠다. ;수정된 title, body 세팅하는거
    public static Post from(String title, String body) {
        return new Post(
                title,
                body
        );
    }
    public Post(String title, String body) {
        setTitle(title);
        setBody(body);
    }
     */
}
