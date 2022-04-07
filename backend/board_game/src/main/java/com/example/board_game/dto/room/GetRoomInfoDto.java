package com.example.board_game.dto.room;

import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetRoomInfoDto {
    private String roomName;
    private List<GetUserDto> users = new ArrayList<>();
    private Long ownerId;

    public GetRoomInfoDto(Room room) {
        this.roomName = room.getName();
        for (User user : room.getUsers()) {
            users.add(new GetUserDto(user));
        }
        this.ownerId = room.getOwner().getId();
    }
}
