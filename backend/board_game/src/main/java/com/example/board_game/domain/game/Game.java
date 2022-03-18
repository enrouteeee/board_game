package com.example.board_game.domain.game;

public abstract class Game {
    private int minOfUser;
    private int maxOfUser;

    public boolean checkStart(int num) {
        return num <= maxOfUser && num >= minOfUser;
    }
}
