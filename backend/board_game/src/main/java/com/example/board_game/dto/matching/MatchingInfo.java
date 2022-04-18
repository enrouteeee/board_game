package com.example.board_game.dto.matching;

import lombok.Getter;

import java.util.List;

@Getter
public class MatchingInfo {
    private Long roomId;
    private List<Long> userIds;

    public MatchingInfo(Long roomId, List<Long> userIds) {
        this.roomId = roomId;
        this.userIds = userIds;
    }
}
