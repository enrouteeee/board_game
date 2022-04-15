package com.example.board_game.service.matching.system;

import com.example.board_game.service.matching.MatchingCondition;

import java.util.List;

public interface MatchingSystem {
    List<Long> enqueue(MatchingCondition condition);
    public void cancel(Long userId);
}
