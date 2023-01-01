package com.final_project_leesanghun_team2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostRequest {
    private String title;
    private String body;
}
