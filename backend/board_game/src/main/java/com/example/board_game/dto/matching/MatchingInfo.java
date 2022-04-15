package com.example.board_game.dto.matching;

import lombok.Getter;

import java.util.List;

@Getter
public class MatchingInfo {
    private Long roomId;
    private List<String> uuIds;

    public MatchingInfo(Long roomId, List<String> uuIds) {
        this.roomId = roomId;
        this.uuIds = uuIds;
    }
}
