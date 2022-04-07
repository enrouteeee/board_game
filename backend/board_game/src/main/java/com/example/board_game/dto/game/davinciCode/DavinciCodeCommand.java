package com.example.board_game.dto.game.davinciCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DavinciCodeCommand {
    public enum DavinciCodeCommandType {
        SELECT_CARD, SELECT_CARD_POSITION, PREDICT_CARD, PASS_TURN, EXIT
    }

    private Long userId;
    private DavinciCodeCommandType type;
    private Long gameId;

    private CardDto card;       // select_card, select_card_position
    private Long boardIdx;      // select_card
    private Long playerCardIdx; // select_card_position, predict_card
    private Long playerIdx;     // predict_card
    private String predictNum;    // predict_card
}