package com.final_project_leesanghun_team2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostDto {
    private Integer id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime createdAt;

}
