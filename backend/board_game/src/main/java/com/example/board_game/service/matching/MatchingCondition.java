package com.example.board_game.service.matching;

import com.example.board_game.domain.game.GameInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MatchingCondition {
    private Long userId;
    private GameInfo gameInfo;
    private int capacity;
}
