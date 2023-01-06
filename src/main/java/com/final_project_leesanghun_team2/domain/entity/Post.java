package com.final_project_leesanghun_team2.domain.entity;

import com.final_project_leesanghun_team2.domain.request.PostAddRequest;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
}
