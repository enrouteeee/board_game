package com.example.board_game.dto.room;

import com.example.board_game.domain.user.User;
import lombok.Getter;

@Getter
public class GetUserDto {
    private Long id;
    private String nickname;

    public GetUserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
