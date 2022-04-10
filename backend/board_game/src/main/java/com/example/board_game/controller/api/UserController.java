package com.example.board_game.controller.api;

import com.example.board_game.auth.UserDto;
import com.example.board_game.dto.user.GetUserDto;
import com.example.board_game.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetUserDto> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();

        return ResponseEntity.ok(userService.getUserInfo(user.getEmail()));
    }

    //닉네임 수정
    @PostMapping
    public ResponseEntity updateNickname(@RequestBody String nickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();

        System.out.println(user.getEmail() + "\nnickname : " + nickname);

        userService.updateNickname(user.getEmail(), nickname);

        return ResponseEntity.ok().build();
    }
}
