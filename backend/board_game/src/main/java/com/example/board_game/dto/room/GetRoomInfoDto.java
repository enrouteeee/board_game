package com.example.board_game.dto.room;

import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetRoomInfoDto {
    private String roomName;
    private List<String> nicknames = new ArrayList<>();
    private String owner;

    public GetRoomInfoDto(Room room) {
        this.roomName = room.getName();
        for (User user : room.getUsers()) {
            nicknames.add(user.getNickname());
        }
        this.owner = room.getOwner().getNickname();
    }
}
