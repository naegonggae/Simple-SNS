package com.final_project_leesanghun_team2.domain.entity;

import com.final_project_leesanghun_team2.domain.UserRole;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String userName;
    private String password;

    @CreatedDate
    private String registeredAt;

    @LastModifiedDate
    private String updatedAt;

    private LocalDateTime removedAt;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @PrePersist
    public void onPrePersist() {
        this.registeredAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updatedAt = this.registeredAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // 회원가입할때 userName, password -> userName, encodedPw 로 포장
    public static User of(String userName, String encodedPw) {
        // Q: 파라미터를 객체로 선언하는게 좋을까? 기본참조형으로 하는게 좋을까?
        // A: 기본참조형으로 가자. 아이디도 비밀번호처럼 커스텀 될 수 있으니까 유지보수 좋게하기 위해 후자로 ㄱㄱ
        return new User(
                userName,
                encodedPw
        );
    }

    // 위에꺼 쓰려고 만들어 놓은것
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
/* 빌더 사용예시
    @Builder
    public PostsDTO(Long id, String title, String body, String userName, String createdAt, String lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userName = userName;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    public static PostsDTO of(Posts posts) {
        return PostsDTO.builder()
                .id(posts.getId())
                .title(posts.getTitle())
                .body(posts.getBody())
                .userName(posts.getUsers().getUserName())
                .createdAt(posts.getCreatedAt())
                .lastModifiedAt(posts.getLastModifiedAt())
                .build();
    }
 */
