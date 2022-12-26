package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.dto.PostRequest;
import com.final_project_leesanghun_team2.domain.response.Response;
import com.final_project_leesanghun_team2.domain.response.PostResponse;
import com.final_project_leesanghun_team2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


}
