package com.example.board_game.dto.game;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DavinciCodeCommand {
    public enum DavinciCodeCommandType {
        GET_BOARD, GET_ORDER, SELECT_CARD, PREDICT_CARD, CHAT
    }

    private String sender;
    private DavinciCodeCommandType type;
    private Long roomId;
    private Map<String, String> command = new HashMap<>();
}
