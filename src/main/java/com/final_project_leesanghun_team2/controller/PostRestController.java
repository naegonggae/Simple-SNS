package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.domain.request.ModifyRequest;
import com.final_project_leesanghun_team2.domain.request.PostAddRequest;
import com.final_project_leesanghun_team2.domain.entity.Post;
import com.final_project_leesanghun_team2.domain.response.*;
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
public class PostRestController {

    private final PostService postService;

    /** 포스트 등록 **/
    @PostMapping
    public Response<PostAddResponse> addPost(@RequestBody PostAddRequest postAddRequest, Authentication authentication) {
        PostAddResponse postAddResponse = postService.add(postAddRequest, authentication);
        return Response.success(postAddResponse);
    }

    @GetMapping
    public ResponseEntity<Response<Page<PostShowResponse>>> getAllPosts(@PageableDefault(size = 20)
                                               @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostShowResponse> postDto = postService.getAllPosts(pageable);
        return ResponseEntity.ok().body(Response.success(postDto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Response<PostShowResponse>> findById(@PathVariable Integer postId) {
        PostShowResponse postShowResponse = postService.findByPost(postId);
        return ResponseEntity.ok().body(Response.success(postShowResponse));
    }

    @PutMapping("/{id}")    // postid → string으로만 오는 거 같은데 숫자형태로 올 수 없는지
    public Response<PostAddResponse> modify(@PathVariable Integer id, @RequestBody ModifyRequest dto, Authentication authentication) {
        System.out.println("Modify Controller Tes1");

        Post post = postService.modify(authentication.getName(), id, dto.getTitle(), dto.getBody());
        System.out.println("Modify Controller Tes3");
        return Response.success(new PostAddResponse("포스트 수정 완료", post.getId()));
    }

    @DeleteMapping("/{postId}")
    public Response<PostAddResponse> delete(@PathVariable Integer postId, Authentication authentication) {
        System.out.println("Delete Controller Tes1");

        postService.delete(authentication.getName(), postId);
        return Response.success(new PostAddResponse("포스트 삭제 완료", postId));
    }


}
