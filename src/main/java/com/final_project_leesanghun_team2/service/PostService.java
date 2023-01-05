package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.entity.Comment;
import com.final_project_leesanghun_team2.domain.entity.Post;
import com.final_project_leesanghun_team2.domain.response.CommentResponse;
import com.final_project_leesanghun_team2.domain.response.PostGetResponse;
import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.repository.CommentRepository;
import com.final_project_leesanghun_team2.repository.PostRepository;
import com.final_project_leesanghun_team2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostGetResponse writePost(String title, String body, String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));
        Post savedPostEntity = postRepository.save(Post.of(title, body, user));

        PostGetResponse postGetResponse = PostGetResponse.builder()
                .id(savedPostEntity.getId())
                .build();

        return postGetResponse;
    }

    public Page<PostGetResponse> getAllPosts(Pageable pageable) {
        Page<Post> post = postRepository.findAll(pageable);
        Page<PostGetResponse> postDto = PostGetResponse.toDtoList(post);
        return postDto;
    }

    public PostGetResponse findByPost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));
        return PostGetResponse.fromEntity(post);
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

    @Transactional
    public Comment write(String comment, String userName, Integer postsId) {
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));
        log.info("postsId:{}", postsId);

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));
        log.info("userName:{}", userName);

        Comment savedComment = commentRepository.save(Comment.of(comment, user, post));
        log.info("comment:{}, user:{}, post:{}", comment, user, post);

        //CommentResponse commentResponse = CommentResponse.fromComment(savedComment);

        return savedComment;
    }

    public Page<Comment> allComment(Pageable pageable, Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));

        //Page<Comment> comments = commentRepository.findAll(pageable);
        //Page<CommentResponse> commentResponses = CommentResponse.toCommentResponse(comments);
        return commentRepository.findAllByPost(post, pageable);
    }

    public Comment modifyComments(String comment, String userName, Integer postsId, Integer id) {
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));


        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));

        Integer userId = user.getId();

        if (!Objects.equals(post.getUser().getId(), userId)) {
            throw new UserSnsException(ErrorCode.INVALID_PERMISSION);
        }

        Comment commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new UserSnsException(ErrorCode.COMMENT_NOT_FOUND));

        commentEntity.setComment(comment);
        Comment savedComment = commentRepository.saveAndFlush(commentEntity);

        return savedComment;
    }
}
