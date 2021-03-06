package com.example.board_game.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomCommand {
    public enum RoomCommandType {
        ENTER, EXIT, CHAT, START
    }

    private Long userId;
    private RoomCommandType type;
    private Long roomId;
    private String message;
}