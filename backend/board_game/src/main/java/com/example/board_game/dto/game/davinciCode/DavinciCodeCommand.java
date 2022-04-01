package com.example.board_game.dto.game.davinciCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DavinciCodeCommand {
    public enum DavinciCodeCommandType {
        SELECT_CARD, SELECT_CARD_POSITION
    }

    private String sender;
    private DavinciCodeCommandType type;
    private Long gameId;
    private Map<String, Object> content = new HashMap<>();
}