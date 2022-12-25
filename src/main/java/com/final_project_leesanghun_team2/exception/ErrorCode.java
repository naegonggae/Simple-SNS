package com.final_project_leesanghun_team2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "User name is Not Found"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Password is Incorrect")
    ;

    private HttpStatus Status;
    private String message;
}
