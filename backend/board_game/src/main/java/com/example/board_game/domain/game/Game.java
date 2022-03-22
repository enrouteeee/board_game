package com.example.board_game.domain.game;

import java.util.List;

public abstract class Game {
    private Long roomId;
    private int numberOfUsers;

    public Game(Long roomId, int numberOfUsers) {
        this.roomId = roomId;
        this.numberOfUsers = numberOfUsers;
    }

    public abstract boolean checkStart();

    public abstract void start(List<Long> userIds);

    public int getNumberOfUsers() {
        return numberOfUsers;
    }
}