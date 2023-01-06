package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.domain.request.*;
import com.final_project_leesanghun_team2.domain.response.UserJoinResponse;
import com.final_project_leesanghun_team2.domain.response.UserLoginResponse;
import com.final_project_leesanghun_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    /** 회원가입 **/
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        return Response.success(userService.join(userJoinRequest));
        // Q: 파라미터가 구성을 직관적으로 보이게 짜는게 좋을까? 아니면 객체로 받는게 좋을까?
        // A: 객체로 받고 추가할거 하면될듯.
    }

    /** 로그인 **/
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginResponse token = userService.login(userLoginRequest);
        return Response.success(token);
    }
}
