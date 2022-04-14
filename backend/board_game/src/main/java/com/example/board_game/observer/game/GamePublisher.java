package com.example.board_game.observer.game;

public interface GamePublisher {
    public void add(GameObserver observer);
    public void delete(GameObserver observer);
    public void notifyGameFinish();
}
