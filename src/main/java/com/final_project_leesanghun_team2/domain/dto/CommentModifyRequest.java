package com.final_project_leesanghun_team2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 이게 뭐길래 추가했다고 되는거지.. https://moonsiri.tistory.com/104
@AllArgsConstructor
@Getter
public class CommentModifyRequest {
    private String comment;
}
