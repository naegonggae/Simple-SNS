package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.domain.request.PostModifyRequest;
import com.final_project_leesanghun_team2.domain.request.PostAddRequest;
import com.final_project_leesanghun_team2.domain.response.*;
import com.final_project_leesanghun_team2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    /** 포스트 등록 **/
    @PostMapping
    public Response<PostResultResponse> addPost(@RequestBody PostAddRequest postAddRequest, Authentication authentication) {
        return Response.success(postService.add(postAddRequest, authentication));
    }

    /** 포스트 조회 **/
    @GetMapping
    public Response<Page<PostShowResponse>> showAllPosts(
            @PageableDefault(size = 20)
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(postService.showAll(pageable));
    }

    /** 포스트 1개 조회 **/
    @GetMapping("/{postId}")
    public Response<PostShowResponse> showOnePost(@PathVariable Integer postId) {
        return Response.success(postService.showOne(postId));
    }

    /** 포스트 수정 **/
    @PutMapping("/{id}")
    public Response<PostResultResponse> modifyPost(@PathVariable Integer id,
                                                   @RequestBody PostModifyRequest postModifyRequest,
                                                   Authentication authentication) {
        return Response.success(postService.modify(id, postModifyRequest, authentication));
    }

    /** 포스트 삭제 **/
    @DeleteMapping("/{postId}")
    public Response<PostResultResponse> delete(@PathVariable Integer postId, Authentication authentication) {
        return Response.success(postService.delete(postId, authentication));
    }
}
