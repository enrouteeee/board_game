package com.example.board_game.dto.user;

import com.example.board_game.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUserDto {
    private Long id;
    private String nickname;

    public GetUserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
