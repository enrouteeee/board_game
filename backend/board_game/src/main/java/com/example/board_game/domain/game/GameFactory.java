package com.example.board_game.domain.game;

import com.example.board_game.domain.game.davincicode.DavinciCode;
import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;

import java.util.List;

public class GameFactory {
    public static Game createGame(GameType gameType, List<User> users, Room room) {
        switch(gameType) {
            case DAVINCI_CODE:
                if(gameType.checkStart(users.size())) {
                   return new DavinciCode(users, room);
                }
            case HALLI_GALLI:
                System.out.println("할리 갈리 생성");
            default:
                throw new IllegalStateException("Unexpected value: " + gameType);
        }
    }
}
