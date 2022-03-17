package com.example.board_game.domain.room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    public Room save(Room room);

    public Optional<Room> findById(Long id);

    public List<Room> findAll();

    public void deleteById(Long id);

    public void clearStore();
}
