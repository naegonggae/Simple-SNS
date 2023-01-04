package com.final_project_leesanghun_team2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_leesanghun_team2.domain.dto.UserJoinRequest;
import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void join_success() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest("sanghun", "1234");

        User user = new User(0, userJoinRequest.getUserName(), userJoinRequest.getPassword());


        when(userService.join(any(), any())).thenReturn(user); // 서비스를 실행시켰을때 결과값을 정해준다. 서비스 테스트가아니니까
        // 여기도 실패이면 thenThrow해줘야함

        mockMvc.perform(post("/api/v1/users/join")
                    .with(csrf()) // security test 추가 후 넣어줘야함
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userJoinRequest))) // userJoinRequest를 json형태로 변경해시켜서 위의 url로 보내줌
                .andDo(print())
                .andExpect(status().isOk()); // 성공이면 isOk, 아니면 에러 출력
    }
    @Test
    @DisplayName("회원가입 실패")
    @WithMockUser
    void join_fail() throws Exception{

        UserJoinRequest userJoinRequest = new UserJoinRequest("sanghun", "1234");

        when(userService.join(any(), any())).thenThrow(new UserSnsException(ErrorCode.DUPLICATED_USER_NAME));

        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DUPLICATED_USER_NAME.getStatus().value()));
    }

    @Test
    @DisplayName("로그인 성공")
    @WithMockUser
    void login_success() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest("sanghun", "1234");

        when(userService.login(any(), any())).thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.jwt").exists());
    }

    @Test
    @DisplayName("로그인 실패 - id 없음")
    @WithMockUser
    void login_fail1() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest("sanghun", "1234");

        // id, password 받아서
        when(userService.login(any(), any())).thenThrow(new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));

        // NOT_FOUND 받으면 잘한것이다.
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 - password 틀림")
    void login_fail2() throws Exception {

        UserJoinRequest userJoinRequest = new UserJoinRequest("sanghun", "1234");

        when(userService.login(any(), any())).thenThrow(new UserSnsException(ErrorCode.INVALID_PASSWORD));

        // NOT_FOUND 받으면 잘한것이다.
        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    //@Nested 테스트 구분
}