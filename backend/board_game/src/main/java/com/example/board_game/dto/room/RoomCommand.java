package com.example.board_game.dto.room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomCommand {
    public enum RoomCommandType {
        ENTER, EXIT, CHAT, START
    }

    private String sender;
    private RoomCommandType type;
    private Long roomId;
    private String message;
}