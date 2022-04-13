package com.example.board_game.domain.game;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryGameRepository implements GameRepository {

    private static final Map<Long, Game> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public Game save(Game game) {
        game.setId(++sequence);
        store.put(sequence, game);
        return game;
    }

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Game> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}
