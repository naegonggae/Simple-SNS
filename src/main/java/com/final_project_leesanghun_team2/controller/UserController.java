package com.final_project_leesanghun_team2.controller;

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
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest dto) {
        UserDto userDto = userService.join(dto);
        UserJoinResponse userJoinResponse = UserJoinResponse.builder()
                .userId(userDto.getId())
                .userName(userDto.getUserName())
                .build();

        return Response.success(userJoinResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<UserLoginResponse>> log(@RequestBody UserLoginRequest dto){
        String token = userService.login(dto.getUserName(), dto.getPassword());
        return ResponseEntity.ok().body(Response.success(new UserLoginResponse(token)));
    }
}
