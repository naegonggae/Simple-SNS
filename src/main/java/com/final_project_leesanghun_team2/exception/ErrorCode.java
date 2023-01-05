package com.final_project_leesanghun_team2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName이 중복됩니다."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "Not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "패스워드가 잘못되었습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurs"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"잘못된 토큰입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not founded");

    private HttpStatus Status;
    private String message;

    public ErrorResponse getErrorResult(){
        return new ErrorResponse(this, this.message);
    }
}
