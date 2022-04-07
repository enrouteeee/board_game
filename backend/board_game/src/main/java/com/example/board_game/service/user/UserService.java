package com.example.board_game.service.user;

import com.example.board_game.domain.user.User;
import com.example.board_game.domain.user.UserRepository;
import com.example.board_game.dto.user.LoginDto;
import com.example.board_game.dto.user.SessionUser;
import com.example.board_game.dto.user.SignUpUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    //회원가입
    public Long singUp(SignUpUserDto dto) {
        User user = new User(dto.getEmail(), dto.getPassword(), dto.getNickname());
        userRepository.save(user);

        return user.getId();
    }

    //로그인
    @Transactional(readOnly = true)
    public SessionUser login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).filter(u -> u.getPassword().equals(dto.getPassword()))
                .orElse(null);

        if(user == null){
            return null;
        }
        return new SessionUser(user);
    }


    //닉네임 변경
    public void updateNickname(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        user.updateNickname(nickname);
    }

    //유저아이디로 유저 찾기
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    //닉네임으로 유저 찾기
    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }
}
