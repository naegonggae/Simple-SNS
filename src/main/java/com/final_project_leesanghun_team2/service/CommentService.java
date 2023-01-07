package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.entity.Comment;
import com.final_project_leesanghun_team2.domain.entity.Post;
import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.domain.request.CommentAddRequest;
import com.final_project_leesanghun_team2.domain.request.CommentModifyRequest;
import com.final_project_leesanghun_team2.domain.response.CommentDeleteResponse;
import com.final_project_leesanghun_team2.domain.response.CommentModifyResponse;
import com.final_project_leesanghun_team2.domain.response.CommentShowResponse;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.repository.CommentRepository;
import com.final_project_leesanghun_team2.repository.PostRepository;
import com.final_project_leesanghun_team2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /** 댓글 등록 **/
    @Transactional
    public CommentShowResponse add(Integer postsId, CommentAddRequest commentAddRequest,
                                   Authentication authentication) {

        // postId의 포스트 찾기
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));

        // UserName 이 있는가
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));

        // 댓글 정보 DB에 저장
        Comment savedComment = commentRepository.save(Comment.of(commentAddRequest.getComment(), user, post));

        // Comment -> CommentShowResponse 형태로 포장
        return CommentShowResponse.of(savedComment);
    }

    /** 댓글 조회 **/
    public Page<CommentShowResponse> showAll(Integer id, Pageable pageable) {

        // postId의 포스트가 있는가
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));

        // findAllByPost 메소드를 사용해서 해당 포스트아이디에 해당하는 댓글을 뽑아온다.
        Page<Comment> comments = commentRepository.findAllByPost(post, pageable);

        return CommentShowResponse.toList(comments);
    }

    /** 댓글 수정 **/
    public CommentModifyResponse modify(Integer postsId, Integer id, CommentModifyRequest commentModifyRequest,
                                        Authentication authentication) {

        // postId의 포스트 찾기
        Post post = postRepository.findById(postsId)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));

        // UserName 이 있는가
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));

        // 해당 postId의 포스트에 저장된 Id와 접속하려는 User 의 Id가 일치하는가 // 권한이 있는가
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new UserSnsException(ErrorCode.INVALID_PERMISSION);
        }

        // 해당 id의 댓글이 있는가
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new UserSnsException(ErrorCode.COMMENT_NOT_FOUND));

        // 댓글 정보 DB에 저장
        comment.setComment(commentModifyRequest.getComment());
        Comment savedComment = commentRepository.save(comment);

        // Comment -> CommentModifyResponse 형태로 포장
        return CommentModifyResponse.ofModify(savedComment);
    }

    /** 댓글 삭제 **/
    @Transactional
    public CommentDeleteResponse delete(Integer postId, Integer id, Authentication authentication) {

        // postId의 포스트 찾기
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserSnsException(ErrorCode.POST_NOT_FOUND));

        // UserName 이 있는가
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND));

        // 해당 postId의 포스트에 저장된 UserName 과 삭제하려는 User 의 UserName 이 일치하는가 ;권한이 있는가
        if (!Objects.equals(post.getUser().getUserName(), user.getUserName())) {
            throw new UserSnsException(ErrorCode.INVALID_PERMISSION); }

        // 해당 id의 댓글이 있는가
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new UserSnsException(ErrorCode.COMMENT_NOT_FOUND));

        // 댓글 삭제
        commentRepository.delete(comment);

        // Comment -> CommentDeleteResponse 로 포장
        return CommentDeleteResponse.of(comment);
    }
}
