package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.entity.Post;
import com.final_project_leesanghun_team2.domain.dto.PostDto;
import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.repository.PostRepository;
import com.final_project_leesanghun_team2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDto writePost(String title, String body, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));
        Post savedPostEntity = postRepository.save(Post.of(title, body, user));

        PostDto postDto = PostDto.builder()
                .id(savedPostEntity.getId())
                .build();

        return postDto;
    }

    public Page<PostDto> getAllPosts(Pageable pageable) {
        Page<Post> post = postRepository.findAll(pageable);
        Page<PostDto> postDto = PostDto.toDtoList(post);
        return postDto;
    }

    public PostDto findByPost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));
        return PostDto.fromEntity(post);
    }

    @Transactional
    public Post modify(String userName, Integer postId, String title, String body) {
        System.out.println("Modify Service Tes1");
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));
        System.out.println("Modify Post");


        System.out.println(userName);
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));

        Integer userId = user.getId();

        if (!Objects.equals(post.getUser().getId(), userId)) {
            throw new UserSnsException(ErrorCode.INVALID_PERMISSION);
        }
        System.out.println("test4");

        post.setTitle(title);
        post.setBody(body);
        Post savedPost = postRepository.saveAndFlush(post);

        return savedPost;
    }

    @Transactional
    public boolean delete(String userName, Integer postId) {
        System.out.println("Delete Service Tes1");
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));
        System.out.println("Delete Post");

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));


        if (!Objects.equals(post.getUser().getUserName(), userName)) {
            throw new UserSnsException(ErrorCode.INVALID_PERMISSION); }

        postRepository.delete(post);

        return true;
    }
}
