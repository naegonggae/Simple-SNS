package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.Response;
import com.final_project_leesanghun_team2.domain.dto.UserDto;
import com.final_project_leesanghun_team2.domain.dto.UserJoinRequest;
import com.final_project_leesanghun_team2.domain.dto.UserJoinResponse;
import com.final_project_leesanghun_team2.domain.dto.UserLoginRequest;
import com.final_project_leesanghun_team2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<String> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto);
        return Response.success("회원가입 성공");
    }

    @PostMapping("/login")
    public Response<String> login(@RequestBody UserLoginRequest dto) {
        userService.login(dto);
        return Response.success("token");
    }
}
