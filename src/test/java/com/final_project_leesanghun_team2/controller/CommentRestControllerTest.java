package com.final_project_leesanghun_team2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_leesanghun_team2.configuration.SecurityConfiguration;
import com.final_project_leesanghun_team2.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(CommentRestControllerTest.class)
class CommentRestControllerTest {

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
    @WithMockUser   // 인증된 상태
    @DisplayName("댓글 작성 성공")
    void comment_success() throws Exception {

        CommentAddRequest commentRequest = new CommentAddRequest("comment");

        User user = UserEntityFixture.get("test", "test");
        Post post = PostEntityFixture.get("test", "test");

        Comment comment = Comment.builder()
                .comment(commentRequest.getComment())
                .id(1)
                .user(user)
                .post(post)
                .build();
        comment.setCreatedAt(LocalDateTime.now());

        when(postService.write(any(), any(), any())).thenReturn(comment);

        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(jsonPath("$.result.createdAt").exists())
        ;
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("댓글 작성 실패 - 로그인하지 않은 경우")
    void comment_fail1() throws Exception {

        CommentAddRequest commentRequest = new CommentAddRequest("comment");

        User user = UserEntityFixture.get("test", "test");
        Post post = PostEntityFixture.get("test", "test");

        Comment comment = Comment.builder()
                .comment(commentRequest.getComment())
                .id(1)
                .user(user)
                .post(post)
                .build();
        comment.setCreatedAt(LocalDateTime.now());

        when(postService.write(any(), any(), any())).thenThrow(new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));

        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("댓글 작성 실패 - 게시물이 존재하지 않은 경우")
    void comment_fail2() throws Exception {

        CommentAddRequest commentRequest = new CommentAddRequest("comment");

        User user = UserEntityFixture.get("test", "test");
        Post post = PostEntityFixture.get("test", "test");

        Comment comment = Comment.builder()
                .comment(commentRequest.getComment())
                .id(1)
                .user(user)
                .post(post)
                .build();
        comment.setCreatedAt(LocalDateTime.now());

        when(postService.write(any(), any(), any())).thenThrow(new UserSnsException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("댓글 조회 성공")
    void getAllComment_success() throws Exception {

        CommentAddRequest commentRequest = new CommentAddRequest("comment");

        User user = UserEntityFixture.get("test", "test");
        Post post = PostEntityFixture.get("test", "test");

        Comment comment = Comment.builder()
                .comment(commentRequest.getComment())
                .id(1)
                .user(user)
                .post(post)
                .build();
        comment.setCreatedAt(LocalDateTime.now());

        when(postService.allComment(any(), any())).thenReturn(Page.empty());


        mockMvc.perform(get("/api/v1/posts/1/comments")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(jsonPath("$.result.createdAt").exists())
        ;
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("댓글 수정 성공")
    void modify_comment_success() throws Exception {

        CommentModifyRequest commentModifyRequest = new CommentModifyRequest("modify comment");

        User user = UserEntityFixture.get("test", "test");
        Post post = PostEntityFixture.get("test", "test");

        Comment comment = Comment.builder()
                .comment(commentModifyRequest.getComment())
                .id(1)
                .user(user)
                .post(post)
                .build();
        comment.setCreatedAt(LocalDateTime.now());
        comment.setLastModifiedAt(LocalDateTime.now());

        when(postService.modifyComments(any(), any(), any(), any()))
                .thenReturn(comment);

        mockMvc.perform(put("/1/comments/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentModifyRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.result.id").exists())
                .andExpect(jsonPath("$.result.comment").exists())
                .andExpect(jsonPath("$.result.userName").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(jsonPath("$.result.createdAt").exists())
                .andExpect(jsonPath("$.result.lastModifiedAt").exists())
                .andExpect(status().isOk());
    }

 */

}