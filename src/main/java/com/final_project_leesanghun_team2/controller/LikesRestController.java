package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class LikesRestController {

    private final LikesService likesService;

    /** 좋아요 누르기 **/
    @PostMapping("/{postId}/likes")
    public Response pushLikes(@PathVariable Integer postId, Authentication authentication) {
        return Response.success(likesService.push(postId, authentication));
    }

    /** 좋아요 개수 **/
    @GetMapping("/{postId}/likes")
    public Response showLikes(@PathVariable Integer postId) {
        return Response.success(likesService.show(postId));
    }
}
