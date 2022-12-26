package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public String createPost() {
        return "";
    }
}
