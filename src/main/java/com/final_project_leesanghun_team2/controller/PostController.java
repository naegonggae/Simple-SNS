package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.dto.CommentModifyRequest;
import com.final_project_leesanghun_team2.domain.dto.CommentRequest;
import com.final_project_leesanghun_team2.domain.dto.ModifyRequest;
import com.final_project_leesanghun_team2.domain.dto.PostRequest;
import com.final_project_leesanghun_team2.domain.entity.Comment;
import com.final_project_leesanghun_team2.domain.entity.Post;
import com.final_project_leesanghun_team2.domain.response.*;
import com.final_project_leesanghun_team2.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostResponse> createPost(@RequestBody PostRequest postRequest, Authentication authentication) {
        PostGetResponse postGetResponse = postService.writePost(postRequest.getTitle(), postRequest.getBody(), authentication.getName());
        return Response.success(new PostResponse("포스트 등록 완료", postGetResponse.getId()));
    }

    @GetMapping
    public ResponseEntity<Response<Page<PostGetResponse>>> getAllPosts(@PageableDefault(size = 20)
                                               @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostGetResponse> postDto = postService.getAllPosts(pageable);
        return ResponseEntity.ok().body(Response.success(postDto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Response<PostGetResponse>> findById(@PathVariable Integer postId) {
        PostGetResponse postGetResponse = postService.findByPost(postId);
        return ResponseEntity.ok().body(Response.success(postGetResponse));
    }

    @PutMapping("/{id}")    // postid → string으로만 오는 거 같은데 숫자형태로 올 수 없는지
    public Response<PostResponse> modify(@PathVariable Integer id, @RequestBody ModifyRequest dto, Authentication authentication) {
        System.out.println("Modify Controller Tes1");

        Post post = postService.modify(authentication.getName(), id, dto.getTitle(), dto.getBody());
        System.out.println("Modify Controller Tes3");
        return Response.success(new PostResponse("포스트 수정 완료", post.getId()));
    }

    @DeleteMapping("/{postId}")
    public Response<PostResponse> delete(@PathVariable Integer postId, Authentication authentication) {
        System.out.println("Delete Controller Tes1");

        postService.delete(authentication.getName(), postId);
        return Response.success(new PostResponse("포스트 삭제 완료", postId));
    }

    @PostMapping("/{postsId}/comments")
    public Response<CommentResponse> writeComment(@PathVariable Integer postsId, @RequestBody CommentRequest commentRequest, Authentication authentication) {
        Comment comment = postService.write(commentRequest.getComment(), authentication.getName(), postsId);
        CommentResponse commentResponse = CommentResponse.fromComment(comment);

        return Response.success(commentResponse);
    }

    //CommentResponse >
    @GetMapping("/{postsId}/comments")
    public ResponseEntity<Response<Page<CommentResponse>>> getAllComment(@PathVariable Integer postsId,
                                                                             @PageableDefault(size = 10)
                                                                             @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CommentResponse> commentResponses = postService.allComment(pageable, postsId)
                .map(comment -> CommentResponse.fromComment(comment));
        return ResponseEntity.ok().body(Response.success(commentResponses));
    }

    @PutMapping("/{postsId}/comments/{id}")
    public Response<CommentModifyResponse> modifyComment(@PathVariable Integer postsId,
                                                         @PathVariable Integer id,
                                                         @RequestBody CommentModifyRequest commentModifyRequest,
                                                         Authentication authentication) {
        Comment comment = postService.modifyComments(commentModifyRequest.getComment(), authentication.getName(), postsId, id);
        CommentModifyResponse commentModifyResponse = CommentModifyResponse.from(comment);


        return Response.success(commentModifyResponse);
    }
}
