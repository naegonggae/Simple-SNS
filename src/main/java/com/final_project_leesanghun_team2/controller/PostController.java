package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.dto.PostRequest;
import com.final_project_leesanghun_team2.domain.response.PostResponse;
import com.final_project_leesanghun_team2.domain.response.Response;
import com.final_project_leesanghun_team2.domain.dto.PostDto;
import com.final_project_leesanghun_team2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostResponse> createPost(@RequestBody PostRequest postRequest, Authentication authentication) {
        PostDto postDto = postService.writePost(postRequest.getTitle(), postRequest.getBody(), authentication.getName());
        return Response.success(new PostResponse("포스트 등록 완료", postDto.getId()));
    }

    @GetMapping
    public Response<Page<PostDto>> getAllPosts(@PageableDefault(size = 20)
                                               @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> postDto = postService.getAllPosts(pageable);
        return Response.success(postDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable Integer postId) {
        PostDto postDto = postService.findByPost(postId);
        return ResponseEntity.ok().body(postDto);
    }
}
