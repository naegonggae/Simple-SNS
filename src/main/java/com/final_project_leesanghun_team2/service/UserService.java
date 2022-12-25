package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.User;
import com.final_project_leesanghun_team2.domain.dto.UserDto;
import com.final_project_leesanghun_team2.domain.dto.UserJoinRequest;
import com.final_project_leesanghun_team2.domain.dto.UserLoginRequest;
import com.final_project_leesanghun_team2.domain.dto.UserLoginResponse;
import com.final_project_leesanghun_team2.exception.ErrorCode;
import com.final_project_leesanghun_team2.exception.UserSnsException;
import com.final_project_leesanghun_team2.repository.UserRepository;
import com.final_project_leesanghun_team2.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expireTimeMS = 1000 * 60 * 60;

    public UserDto join(UserJoinRequest userJoinRequest) {

        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user ->
                {throw new UserSnsException(ErrorCode.DUPLICATED_USER_NAME, "Not founded");
                });

        User savedUSer = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserDto.builder()
                .id(savedUSer.getId())
                .userName(savedUSer.getUserName())
                .build();
    }

    public String login(UserLoginRequest dto) {

        User user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND,
            "UserName이 중복됩니다."));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserSnsException(ErrorCode.INVALID_PASSWORD, "패스워드가 잘못되었습니다.");
        }

        return JwtTokenUtil.createToken(dto.getUserName(), secretKey, expireTimeMS);
    }
}
