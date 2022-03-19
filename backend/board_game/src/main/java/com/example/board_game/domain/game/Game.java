package com.example.board_game.domain.game;

public abstract class Game {
    private Long roomId;
    private int numberOfUsers;

    public Game(Long roomId, int numberOfUsers) {
        this.roomId = roomId;
        this.numberOfUsers = numberOfUsers;
    }

    public abstract boolean checkStart();

    public abstract void start();

    public int getNumberOfUsers() {
        return numberOfUsers;
    }
}