package com.final_project_leesanghun_team2.domain.dto;

import com.final_project_leesanghun_team2.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private String userName;
    private String password;

    public User toEntity() {
        return User.builder()
                .userName(this.userName)
                .password(this.password)
                .build();
    }
}