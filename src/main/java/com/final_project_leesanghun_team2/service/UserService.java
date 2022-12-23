package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.dto.UserDto;
import com.final_project_leesanghun_team2.domain.dto.UserJoinRequest;
import com.final_project_leesanghun_team2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto join(UserJoinRequest userJoinRequest) {
        return new UserDto("", "");
    }
}
