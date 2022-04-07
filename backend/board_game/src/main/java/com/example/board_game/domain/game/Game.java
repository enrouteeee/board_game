package com.example.board_game.domain.game;

import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;

import java.util.List;

public abstract class Game {
    private Long id;
    private final int numberOfUsers;
    private Room room;

    public Game(Long id, int numberOfUsers, Room room) {
        this.id = id;
        this.numberOfUsers = numberOfUsers;
        this.room = room;
    }

    public abstract boolean checkStart();

    public abstract void start(List<User> users);

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public abstract void exitPlayer(Long playerId);

    protected Room getRoom() {
        return this.room;
    }
}