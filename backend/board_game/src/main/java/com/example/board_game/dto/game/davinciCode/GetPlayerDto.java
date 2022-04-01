package com.example.board_game.dto.game.davinciCode;

import com.example.board_game.domain.game.davincicode.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetPlayerDto {
    private Long id;
    private String nickname;

    public GetPlayerDto(Player player) {
        this.id = player.getId();
        this.nickname = player.getNickname();
    }
}
