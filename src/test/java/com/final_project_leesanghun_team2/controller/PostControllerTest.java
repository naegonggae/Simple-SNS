package com.final_project_leesanghun_team2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_leesanghun_team2.configuration.SecurityConfiguration;
import com.final_project_leesanghun_team2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PostRestController.class)
class PostControllerTest {

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
    @DisplayName("포스트 조회 성공")
    void post_read_success() throws Exception {

        PostShowResponse post = PostShowResponse.builder()
                .id(1)
                .title("This is a post.")
                .body("This is the body.")
                .userName("kyeongrok")
                .createdAt(LocalDateTime.now())
                .build();

        when(postService.findByPost(any()))
                .thenReturn(post);

        mockMvc.perform(get("/api/v1/posts/1")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(post)))

                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.body").exists())
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("pageable 파라미터 검증")
    void evaluates_pageable_parameter() throws Exception {

        mockMvc.perform(get("/api/v1/posts")
                        .param("page", "0")
                        .param("size", "3")
                        .param("sort", "createdAt,desc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(postService).getAllPosts(pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        assertEquals(0, pageable.getPageNumber());
        assertEquals(3, pageable.getPageSize());
        assertEquals(Sort.by("createdAt", "desc"), pageable.withSort(Sort.by("createdAt", "desc")).getSort());
    }



    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 작성 성공")
    void post_success() throws Exception {

        PostAddRequest postRequest = new PostAddRequest("title_post", "body_post");

        when(postService.writePost(any(), any(), any()))
                .thenReturn(PostShowResponse.builder()
                        .id(0)
                        .build());

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
        ;
    }

    @Test
    @WithAnonymousUser // 인증 된지 않은 상태
    @DisplayName("포스트 작성 실패(1) : 인증 실패")
    void post_fail1() throws Exception {

        PostAddRequest postRequest = new PostAddRequest("title_post", "body_post");

        when(postService.writePost(any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 수정 성공")
    void modify_success() throws Exception {

        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("title_modify")
                .body("body_modify")
                .build();

        Post post = Post.builder()
                .id(1)
                .build();

        when(postService.modify(any(), any(), any(), any()))
                .thenReturn(post);

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser // 인증 되지 않은 상태
    @DisplayName("포스트 수정 실패(1) : 인증 실패")
    void modify_fail1() throws Exception {

        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("title_modify")
                .body("body_modify")
                .build();

        when(postService.modify(any(), any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 수정 실패(2) : 포스트 내용 불일치")
    void modify_fail2() throws Exception {

        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("title_modify")
                .body("body_modify")
                .build();

        when(postService.modify(any(), any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getStatus().value()));
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 수정 실패(3) : 작성자 불일치")
    void modify_fail3() throws Exception {

        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("title_modify")
                .body("body_modify")
                .build();

        when(postService.modify(any(), any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 수정 실패(4) : 데이터베이스 에러")
    void modify_fail4() throws Exception {

        PostModifyRequest modifyRequest = PostModifyRequest.builder()
                .title("title_modify")
                .body("body_modify")
                .build();

        when(postService.modify(any(), any(), any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(modifyRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getStatus().value()));
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 삭제 성공")
    void delete_success() throws Exception {

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result.message").exists())
                .andExpect(jsonPath("$.result.postId").exists())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser // 인증 된지 않은 상태
    @DisplayName("포스트 삭제 실패(1) : 인증 실패")
    void delete_fail1() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 삭제 실패(2) : 포스트 내용 불일치")
    void delete_fail2() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getStatus().value()));
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 삭제 실패(3) : 작성자 불일치")
    void delete_fail3() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("포스트 삭제 실패(4) : 데이터베이스 에러")
    void delete_fail4() throws Exception {

        when(postService.delete(any(), any()))
                .thenThrow(new UserSnsException(ErrorCode.DATABASE_ERROR));

        mockMvc.perform(delete("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(ErrorCode.DATABASE_ERROR.getStatus().value()));
    }

    @Test
    @WithMockUser   // 인증된 상태
    @DisplayName("댓글 작성 성공")
    void comment_success() throws Exception {

        CommentRequest commentRequest = new CommentRequest("comment");

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

        CommentRequest commentRequest = new CommentRequest("comment");

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

        CommentRequest commentRequest = new CommentRequest("comment");

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

        CommentRequest commentRequest = new CommentRequest("comment");

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