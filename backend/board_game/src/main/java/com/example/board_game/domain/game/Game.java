package com.example.board_game.domain.game;

import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;
import com.example.board_game.observer.game.GameObserver;
import com.example.board_game.observer.game.GamePublisher;

import java.util.ArrayList;
import java.util.List;

public abstract class Game implements GamePublisher {
    private Long id;
    private LeaderBoard leaderBoard;

    private final List<GameObserver> observers = new ArrayList<>();

    public Game(Room room) {
        this.id = room.getId();
        this.leaderBoard = new LeaderBoard(room.getNumberOfUsers(), room.getGameInfo());
        init(room.getUsers());
    }

    public abstract void init(List<User> users);

    public abstract void exitPlayer(Long playerId);

    public void updateLeaderBoard(Long playerId) {
        leaderBoard.update(playerId);
    }

    public void finish() {
        notifyGameFinish();
    }

    public Long getId() {
        return this.id;
    }

    public LeaderBoard getLeaderBoard() {
        return this.leaderBoard;
    }

    @Override
    public void add(GameObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyGameFinish() {
        for (GameObserver observer : this.observers) {
            observer.gameFinished(this);
        }
    }
}
