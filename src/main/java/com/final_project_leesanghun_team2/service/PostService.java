package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.dto.PostRequest;
import com.final_project_leesanghun_team2.domain.entity.Post;
import com.final_project_leesanghun_team2.domain.dto.PostDto;
import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.repository.PostRepository;
import com.final_project_leesanghun_team2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDto writePost(String title, String body, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND, String.format("%s not founded", userName)));
        Post savedPostEntity = postRepository.save(Post.of(title, body, user));

        PostDto postDto = PostDto.builder()
                .id(savedPostEntity.getId())
                .build();

        return postDto;
    }
}
