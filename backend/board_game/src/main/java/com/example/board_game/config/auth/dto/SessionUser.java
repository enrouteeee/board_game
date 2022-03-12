package com.example.board_game.config.auth.dto;

import com.example.board_game.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 세션에 사용자 정보를 저장하기 위한 Dto 클래스
 * Entity에 직접 직렬화를 구현하지 않음(성능 이슈, 부수 효과 발생 확률)
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
