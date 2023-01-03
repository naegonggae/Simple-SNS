package com.final_project_leesanghun_team2.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY) // comment랑 양방향이였어
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    public static Post of(String title, String body, User user) {
        Post entity = new Post();
        entity.setTitle(title);
        entity.setBody(body);
        entity.setUser(user);
        return entity;
    }
}
