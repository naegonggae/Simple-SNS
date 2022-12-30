package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.domain.dto.UserDto;
import com.final_project_leesanghun_team2.domain.dto.UserJoinRequest;
import com.final_project_leesanghun_team2.domain.dto.UserLoginRequest;
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
    private Long expireTimeMS = 1000 * 60 * 60l;

    public UserDto join(UserJoinRequest userJoinRequest) {

        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user ->
                {throw new UserSnsException(ErrorCode.DUPLICATED_USER_NAME,
                        String.format("%s는 이미 사용중인 아이디입니다.", userJoinRequest.getUserName()));
                });

        User savedUSer = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserDto.builder()
                .id(savedUSer.getId())
                .userName(savedUSer.getUserName())
                .build();
    }

    public String login(String userName, String password) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND,
            String.format("%s는 가입된적이 없습니다.", userName)));

        if (!encoder.matches(user.getPassword(), user.getPassword())) {
            throw new UserSnsException(ErrorCode.INVALID_PASSWORD, "비밀번호가 틀렸습니다.");
        }

        String token = JwtTokenUtil.createToken("dal", secretKey, expireTimeMS);
        return token;
    }
}
