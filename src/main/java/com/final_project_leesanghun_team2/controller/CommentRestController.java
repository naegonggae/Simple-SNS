package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.domain.request.CommentAddRequest;
import com.final_project_leesanghun_team2.domain.request.CommentModifyRequest;
import com.final_project_leesanghun_team2.domain.response.CommentDeleteResponse;
import com.final_project_leesanghun_team2.domain.response.CommentModifyResponse;
import com.final_project_leesanghun_team2.domain.response.CommentShowResponse;
import com.final_project_leesanghun_team2.service.CommentService;
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
public class CommentRestController {

    private final CommentService commentService;

    /** 댓글 등록 **/
    @PostMapping("/{postsId}/comments")
    public Response<CommentShowResponse> addComment(@PathVariable Integer postsId,
                                                    @RequestBody CommentAddRequest commentAddRequest,
                                                    Authentication authentication) {
        return Response.success(commentService.add(postsId, commentAddRequest, authentication));
    }

    /** 댓글 조회 **/
    @GetMapping("/{postsId}/comments")
    public Response<Page<CommentShowResponse>> showAllComment(@PathVariable Integer postsId, @PageableDefault(size = 10)
                                                             @SortDefault(sort = "createdAt",direction = Sort.Direction.DESC)
                                                             Pageable pageable) {
        return Response.success(commentService.showAll(postsId, pageable));
    }

    /** 댓글 수정 **/
    @PutMapping("/{postsId}/comments/{id}")
    public Response<CommentModifyResponse> modifyComment(@PathVariable Integer postsId,
                                                         @PathVariable Integer id,
                                                         @RequestBody CommentModifyRequest commentModifyRequest,
                                                         Authentication authentication) {
        return Response.success(commentService.modify(postsId, id, commentModifyRequest ,authentication));
    }

    /** 댓글 삭제 **/
    @DeleteMapping("/{postsId}/comments/{id}")
    public Response<CommentDeleteResponse> deleteComment(@PathVariable Integer postsId,
                                                         @PathVariable Integer id,
                                                         Authentication authentication) {
        return Response.success(commentService.delete(postsId, id, authentication));
    }
}
