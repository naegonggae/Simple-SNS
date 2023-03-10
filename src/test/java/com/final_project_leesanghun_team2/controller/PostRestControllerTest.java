package com.final_project_leesanghun_team2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_leesanghun_team2.configuration.SecurityConfiguration;
import com.final_project_leesanghun_team2.domain.request.PostAddRequest;
import com.final_project_leesanghun_team2.domain.request.PostModifyRequest;
import com.final_project_leesanghun_team2.domain.response.PostResultResponse;
import com.final_project_leesanghun_team2.domain.response.PostShowResponse;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostRestController.class)
class PostRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @MockBean
    SecurityConfiguration securityConfiguration;

    @Autowired
    ObjectMapper objectMapper;
/*
    @Test
    @WithMockUser   // ????????? ??????
    @DisplayName("????????? ?????? ??????")
    void show_post_success() throws Exception {

        PostShowResponse postShowResponse = new PostShowResponse(1, "??????", "??????", "??????", null);

        when(postService.showAll(any()))
                .thenReturn();

        mockMvc.perform(get("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.body").exists());

    }
 */


    @Test // ?????? ??????...
    @WithMockUser // ????????? ??????
    @DisplayName("pageable ???????????? ??????")
    void evaluates_pageable_parameter() throws Exception {

        mockMvc.perform(get("/api/v1/posts")
                        .param("page", "0")
                        .param("size", "3")
                        .param("sort", "createdAt,desc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(postService).showAll(pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        assertEquals(0, pageable.getPageNumber());
        assertEquals(3, pageable.getPageSize());
        assertEquals(Sort.by("createdAt", "desc"), pageable.withSort(Sort.by("createdAt", "desc")).getSort());
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????")
    void add_post_success() throws Exception {

        PostAddRequest postRequest = new PostAddRequest("??????", "??????");
        PostResultResponse postResultResponse = new PostResultResponse("?????????", 1);

        when(postService.add(any(), any()))
                .thenReturn(postResultResponse);

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists());
    }

    @Test
    @WithAnonymousUser // ?????? ?????? ?????? ??????
    @DisplayName("????????? ?????? ??????(1) : ?????? ??????")
    void add_post_fail1() throws Exception {

        PostAddRequest postRequest = new PostAddRequest("??????", "??????");
        PostResultResponse postResultResponse = new PostResultResponse("?????????", 1);

        when(postService.add(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    //????????? ?????? ??????(1) - ?????? ?????? - JWT??? Bearer Token?????? ????????? ?????? ??????
    //????????? ?????? ??????(2) - ?????? ?????? - JWT??? ???????????? ?????? ??????
    //????????? ?????? ??????

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????")
    void modify_post_success() throws Exception {

        PostModifyRequest postModifyRequest = new PostModifyRequest("??????", "??????");
        PostResultResponse postResultResponse = new PostResultResponse("?????????", 1);

        when(postService.modify(any(), any(), any()))
                .thenReturn(postResultResponse);

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postModifyRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists());
    }

    @Test
    @WithAnonymousUser // ?????? ?????? ?????? ??????
    @DisplayName("????????? ?????? ??????(1) : ?????? ??????")
    void modify_post_fail1() throws Exception {

        PostModifyRequest postModifyRequest = new PostModifyRequest("??????", "??????");

        when(postService.modify(any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postModifyRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????(2) : ????????? ?????? ?????????")
    void modify_post_fail2() throws Exception {

        PostModifyRequest postModifyRequest = new PostModifyRequest("??????", "??????");

        when(postService.modify(any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postModifyRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getStatus().value()));
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????(3) : ????????? ?????????")
    void modify_post_fail3() throws Exception {

        PostModifyRequest postModifyRequest = new PostModifyRequest("??????", "??????");

        when(postService.modify(any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postModifyRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????(4) : ?????????????????? ??????")
    void modify_post_fail4() throws Exception {

        PostModifyRequest postModifyRequest = new PostModifyRequest("??????", "??????");

        when(postService.modify(any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postModifyRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getStatus().value()));
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????")
    void delete_post_success() throws Exception {

        PostResultResponse postResultResponse = new PostResultResponse("?????????", 1);

        when(postService.delete(any(), any()))
                .thenReturn(postResultResponse);

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").exists())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists());
    }

    @Test
    @WithAnonymousUser // ?????? ?????? ?????? ??????
    @DisplayName("????????? ?????? ??????(1) : ?????? ??????")
    void delete_post_fail1() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????(2) : ????????? ?????? ?????????")
    void delete_post_fail2() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getStatus().value()));
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????(3) : ????????? ?????????")
    void delete_post_fail3() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser // ????????? ??????
    @DisplayName("????????? ?????? ??????(4) : ?????????????????? ??????")
    void delete_post_fail4() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getStatus().value()));
    }
}