package com.example.board_game.domain.game;

import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;

import java.util.List;

public abstract class Game {
    private Long id;
    private Room room;

    public Game(List<User> users, Room room) {
        this.room = room;
        init(users);
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public abstract void init(List<User> users);

    public abstract void exitPlayer(Long playerId);

    public void finish() {
        getRoom().finishGame();
    }

    public Long getId() {
        return this.id;
    }

    private Room getRoom() {
        return this.room;
    }
}