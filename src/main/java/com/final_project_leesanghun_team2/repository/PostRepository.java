package com.final_project_leesanghun_team2.repository;

import com.final_project_leesanghun_team2.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
