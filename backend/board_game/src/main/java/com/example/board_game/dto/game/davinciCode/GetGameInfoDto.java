package com.example.board_game.dto.game.davinciCode;

import com.example.board_game.domain.game.davincicode.Card;
import com.example.board_game.domain.game.davincicode.DavinciCode;
import com.example.board_game.domain.game.davincicode.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetGameInfoDto {
    private List<Card> board;
    private List<GetPlayerDto> order = new ArrayList<>();

    public GetGameInfoDto(DavinciCode game) {
        this.board = game.getCards();
        for (Player player : game.getPlayers()) {
            order.add(new GetPlayerDto(player));
        }
    }
}
