package com.final_project_leesanghun_team2.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class PostResponse {
    private Integer id;
    private String title;
    private String body;
    private String userName;
    private Timestamp created_at;
    private Timestamp last_modified_at;
}
