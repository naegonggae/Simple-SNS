package com.final_project_leesanghun_team2.repository;

import com.final_project_leesanghun_team2.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
