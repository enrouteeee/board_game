package com.example.board_game.domain.game;

import lombok.Getter;

import java.util.Arrays;

/*
순위표
 */
@Getter
public class LeaderBoard {
    private final Long[] board;
    private int count;

    private final GameInfo gameInfo;

    public LeaderBoard(int size, GameInfo gameInfo) {
        board = new Long[size];
        this.count = size - 1;

        this.gameInfo = gameInfo;
    }

    public void update(Long playerId) {
        if(count < 0){
            throw new IllegalArgumentException("더이상 update 할 수 없습니다.");
        }
        board[count--] = playerId;
    }

    @Override
    public String toString() {
        return "Standing{" +
                "\n gameInfo=" + gameInfo +
                "\nstanding=\n" + Arrays.toString(board) +
                "\n}";
    }
}
