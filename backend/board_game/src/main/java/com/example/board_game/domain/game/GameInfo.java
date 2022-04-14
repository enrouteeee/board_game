package com.example.board_game.domain.game;

import com.example.board_game.domain.game.davincicode.DavinciCode;
import com.example.board_game.domain.room.Room;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameInfo {
    DAVINCI_CODE(2, 4),
    HALLI_GALLI(2, 6);

    private final int minNumOfPlayer;
    private final int maxNumOfPlayer;

    public boolean checkStart(int num) {
        return num <= maxNumOfPlayer && num >= minNumOfPlayer;
    }

    public Game createGame(Room room) {
        switch(this) {
            case DAVINCI_CODE:
                if(this.checkStart(room.getNumberOfUsers())) {
                    return new DavinciCode(room);
                }
            case HALLI_GALLI:
                System.out.println("할리 갈리 생성");
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
