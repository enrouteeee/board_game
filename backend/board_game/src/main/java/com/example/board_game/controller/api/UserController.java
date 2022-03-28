package com.example.board_game.controller.api;

import com.example.board_game.dto.user.LoginDto;
import com.example.board_game.dto.user.SessionUser;
import com.example.board_game.dto.user.SignUpUserDto;
import com.example.board_game.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpUserDto dto) {
        userService.singUp(dto);

        return ResponseEntity.ok().build();
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto dto, HttpSession session) {
        SessionUser user = userService.login(dto);

        if(user == null){
            throw new IllegalArgumentException("아이디 비밀번호가 틀렸습니다.");
        }
        session.setAttribute("user", user);

        return ResponseEntity.ok().build();
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }

    //닉네임 수정
    @PostMapping("/user/{userId}")
    public ResponseEntity updateNickname(@RequestBody String nickname,
                                         @PathVariable("userId") Long userId) {
        userService.updateNickname(userId, nickname);

        return ResponseEntity.ok().build();
    }
}
