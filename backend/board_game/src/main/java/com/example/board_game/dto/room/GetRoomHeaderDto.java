package com.example.board_game.dto.room;

import com.example.board_game.domain.room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRoomHeaderDto {
    private Long id;
    private String name;
    private int capacity;
    private int numberOfUsers;

    public GetRoomHeaderDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.capacity = room.getCapacity();
        this.numberOfUsers = room.getNumberOfUsers();
    }
}
