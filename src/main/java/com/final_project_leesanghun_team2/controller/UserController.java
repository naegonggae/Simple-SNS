package com.final_project_leesanghun_team2.controller;

import com.final_project_leesanghun_team2.domain.entity.User;
import com.final_project_leesanghun_team2.domain.response.Response;
import com.final_project_leesanghun_team2.domain.dto.*;
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

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        User user = userService.join(userJoinRequest.getUserName(), userJoinRequest.getPassword());
        UserJoinResponse userJoinResponse = UserJoinResponse.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .build();
        return Response.success(userJoinResponse); // 강사님은 왜 User로 받았을까? UserJoinResponse로 바로 받을 수 있을텐데
    }

    @PostMapping("/login")
    public ResponseEntity<Response<UserLoginResponse>> login(@RequestBody UserLoginRequest userLoginRequest){
        String token = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return ResponseEntity.ok().body(Response.success(new UserLoginResponse(token)));
    }
}
