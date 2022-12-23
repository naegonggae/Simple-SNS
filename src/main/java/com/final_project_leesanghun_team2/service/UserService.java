package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.User;
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

        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user -> {throw new RuntimeException("해당 UserName이 중복됩니다.");
                });

        User savedUSer = userRepository.save(userJoinRequest.toEntity());
        return UserDto.builder()
                .id(savedUSer.getId())
                .userName(savedUSer.getUserName())
                .password(savedUSer.getPassword())
                .build();
    }
}
