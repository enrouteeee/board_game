package com.example.board_game.domain.room;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoomRepositoryMemory implements RoomRepository {
    private static final Map<Long, Room> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    @Override
    public synchronized Room save(Room room) {
        room.setId(++sequence);
        store.put(room.getId(), room);
        return room;
    }

    @Override
    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Room> findAll() {
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
