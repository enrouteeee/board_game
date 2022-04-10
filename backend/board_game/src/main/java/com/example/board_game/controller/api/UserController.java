package com.example.board_game.controller.api;

import com.example.board_game.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    //닉네임 수정
    @PostMapping("/user/{userId}")
    public ResponseEntity updateNickname(@RequestBody String nickname,
                                         @PathVariable("userId") Long userId) {
        userService.updateNickname(userId, nickname);

        return ResponseEntity.ok().build();
    }
}
