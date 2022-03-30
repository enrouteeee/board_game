package com.example.board_game.dto.room;

import com.example.board_game.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetRoomInfoDto {
    private String roomName;
    private List<String> nicknames = new ArrayList<>();

    public GetRoomInfoDto(String name, List<User> users) {
        this.roomName = name;
        for (User user : users) {
            nicknames.add(user.getNickname());
        }
    }
}
