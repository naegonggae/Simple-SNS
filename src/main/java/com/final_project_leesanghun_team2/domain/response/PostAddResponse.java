package com.final_project_leesanghun_team2.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostAddResponse {
    private String message;
    private Integer postId;

    // 포스트 등록할때 PostAddResponse 에 DB에 저장된 post_id 연결
    public static PostAddResponse of(Integer postId) {
        return new PostAddResponse(
                "포스트 등록 완료",
                postId
        );
    }
}
