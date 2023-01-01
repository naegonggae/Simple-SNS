package com.final_project_leesanghun_team2.domain.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String userName;
    private String password;

    public static User of(String userName, String encodedPwd) {
        User entity = new User();
        entity.setUserName(userName);
        entity.setPassword(encodedPwd);
        return entity;
    }

    public static User fromEntity(User user) {
        return new User(
                user.getId(),
                user.getUserName(),
                user.getPassword()
                //user.getRole(),
                //user.getRegisteredAt(),
                //user.getUpdatedAt(),
                //user.getRemovedAt()
        );
    }
}
