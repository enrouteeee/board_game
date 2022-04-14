package com.example.board_game.observer.game;

import com.example.board_game.domain.game.Game;

public interface GameObserver {
    public void gameFinished(Game game);
}
