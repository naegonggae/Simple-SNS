package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.domain.request.*;
import com.final_project_leesanghun_team2.domain.response.UserJoinResponse;
import com.final_project_leesanghun_team2.domain.response.UserLoginResponse;
import com.final_project_leesanghun_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 회원가입 **/
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        return Response.success(userService.join(userJoinRequest));
    }

    /** 로그인 **/
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginResponse token = userService.login(userLoginRequest);
        return Response.success(token);
    }
}
