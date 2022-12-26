package com.final_project_leesanghun_team2.domain.response;

import com.final_project_leesanghun_team2.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private ErrorCode errorCode;
    private String message;
}