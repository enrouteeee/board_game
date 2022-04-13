package com.example.board_game.domain.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameType {
    DAVINCI_CODE(2, 4),
    HALLI_GALLI(2, 6);

    private final int minNumOfPlayer;
    private final int maxNumOfPlayer;

    public boolean checkStart(int num) {
        return num <= maxNumOfPlayer && num >= minNumOfPlayer;
    }
}
