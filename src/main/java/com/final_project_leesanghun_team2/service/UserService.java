package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.User;
import com.final_project_leesanghun_team2.domain.dto.UserDto;
import com.final_project_leesanghun_team2.domain.dto.UserJoinRequest;
import com.final_project_leesanghun_team2.domain.dto.UserLoginRequest;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserDto join(UserJoinRequest userJoinRequest) {

        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user ->
                {throw new UserSnsException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName:%s", userJoinRequest.getUserName()));
                });

        User savedUSer = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserDto.builder()
                .id(savedUSer.getId())
                .userName(savedUSer.getUserName())
                .build();
    }

    public String login(UserLoginRequest dto) {

        User user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new UserSnsException(ErrorCode.NOT_FOUND,
            String.format("%s는 가입된적이 없습니다.", dto.getUserName())));

        if (!encoder.matches(dto.getUserName(), user.getUserName())) {
            throw new UserSnsException(ErrorCode.INVALID_PASSWORD, String.format("%s 또는 %s가 잘못됐습니다.", dto.getUserName(), dto.getPassword()));
        }

        return "";
    }
}
