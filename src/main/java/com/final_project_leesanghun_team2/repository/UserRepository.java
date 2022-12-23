package com.final_project_leesanghun_team2.repository;

import com.final_project_leesanghun_team2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
