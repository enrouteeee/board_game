package com.example.board_game.domain.game;

import com.example.board_game.domain.user.User;

import java.util.List;

public abstract class Game {
    private Long id;
    private final int numberOfUsers;

    public Game(Long id, int numberOfUsers) {
        this.id = id;
        this.numberOfUsers = numberOfUsers;
    }

    public abstract boolean checkStart();

    public abstract void start(List<User> users);

    public int getNumberOfUsers() {
        return numberOfUsers;
    }
}