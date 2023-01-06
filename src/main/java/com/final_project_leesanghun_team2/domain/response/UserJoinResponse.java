package com.final_project_leesanghun_team2.domain.response;

import com.final_project_leesanghun_team2.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJoinResponse {
    private Integer userId;
    private String userName;

    // User 를 UserJoinResponse 형태로 변경
    public static UserJoinResponse of(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getUserName()
        );
    }
}
