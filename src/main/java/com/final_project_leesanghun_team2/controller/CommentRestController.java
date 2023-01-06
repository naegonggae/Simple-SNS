package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.domain.entity.Comment;
import com.final_project_leesanghun_team2.domain.request.CommentModifyRequest;
import com.final_project_leesanghun_team2.domain.request.CommentRequest;
import com.final_project_leesanghun_team2.domain.response.CommentDeleteResponse;
import com.final_project_leesanghun_team2.domain.response.CommentModifyResponse;
import com.final_project_leesanghun_team2.domain.response.CommentResponse;
import com.final_project_leesanghun_team2.service.CommentService;
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
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/{postsId}/comments")
    public Response<CommentResponse> writeComment(@PathVariable Integer postsId, @RequestBody CommentRequest commentRequest, Authentication authentication) {
        Comment comment = commentService.write(commentRequest.getComment(), authentication.getName(), postsId);
        CommentResponse commentResponse = CommentResponse.fromComment(comment);

        return Response.success(commentResponse);
    }

    //CommentResponse >
    @GetMapping("/{postsId}/comments")
    public ResponseEntity<Response<Page<CommentResponse>>> getAllComment(@PathVariable Integer postsId,
                                                                         @PageableDefault(size = 10)
                                                                         @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CommentResponse> commentResponses = commentService.allComment(pageable, postsId)
                .map(comment -> CommentResponse.fromComment(comment));
        return ResponseEntity.ok().body(Response.success(commentResponses));
    }

    @PutMapping("/{postsId}/comments/{id}")
    public Response<CommentModifyResponse> modifyComment(@PathVariable Integer postsId,
                                                         @PathVariable Integer id,
                                                         @RequestBody CommentModifyRequest commentModifyRequest,
                                                         Authentication authentication) {
        Comment comment = commentService.modifyComments(commentModifyRequest.getComment(), authentication.getName(), postsId, id);
        CommentModifyResponse commentModifyResponse = CommentModifyResponse.from(comment);


        return Response.success(commentModifyResponse);
    }
    @DeleteMapping("/{postsId}/comments/{id}")
    public Response<CommentDeleteResponse> deleteComment(@PathVariable Integer postsId,
                                                         @PathVariable Integer id,
                                                         Authentication authentication) {
        commentService.deleteComments(postsId, authentication.getName(), id);

        return Response.success(new CommentDeleteResponse("댓글 삭제 완료", id));
    }
}
