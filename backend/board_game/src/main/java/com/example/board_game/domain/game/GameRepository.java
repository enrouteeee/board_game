package com.example.board_game.domain.game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    public Game save(Game game);

    public Optional<Game> findById(Long id);

    public List<Game> findAll();

    public void deleteById(Long id);

    public void clearStore();
}
