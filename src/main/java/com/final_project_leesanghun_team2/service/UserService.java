package com.final_project_leesanghun_team2.service;

import com.final_project_leesanghun_team2.domain.entity.User;
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

    public User join(String userName, String password){
        userRepository.findByUserName(userName).ifPresent(
                user -> {
                    throw new UserSnsException(ErrorCode.DUPLICATED_USER_NAME, " "+ userName +"는 이미 있습니다.");
                }
        );
        User user = User.of(userName, encoder.encode(password));

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public String login(String userName, String password) {

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserSnsException(ErrorCode.USERNAME_NOT_FOUND,
            String.format("%s는 가입된적이 없습니다.", userName)));

        User savedUser = User.fromEntity(user);

        if (!encoder.matches(password, user.getPassword())) {
            throw new UserSnsException(ErrorCode.INVALID_PASSWORD, "비밀번호가 틀렸습니다.");
        }

        String token = JwtTokenUtil.createToken(savedUser.getUserName(), secretKey, expireTimeMS);
        return token;
    }
}
