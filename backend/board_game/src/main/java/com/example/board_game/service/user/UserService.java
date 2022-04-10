package com.example.board_game.service.user;

import com.example.board_game.domain.user.User;
import com.example.board_game.domain.user.UserRepository;
import com.example.board_game.dto.user.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public GetUserDto getUserInfo(String email) {
        User user = findUserByEmail(email);

        return new GetUserDto(user);
    }

    // 닉네임 변경
    public void updateNickname(String email, String nickname) {
        User user = findUserByEmail(email);

        user.updateNickname(nickname);
    }

    // PK로 유저 찾기
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    // email로 유저 찾기
    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }
}
