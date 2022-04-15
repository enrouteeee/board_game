package com.example.board_game.service.matching.system;

import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.matching.MatchingQueue;
import com.example.board_game.service.matching.MatchingCondition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class FIFOMatchingSystem implements MatchingSystem {

    private MatchingQueue matchingQueue;

    @PostConstruct
    public void init() {
        matchingQueue = new MatchingQueue(6, GameInfo.values().length);
    }

    @Override
    public List<Long> enqueue(MatchingCondition condition) {
        return matchingQueue.
                enQueue(condition.getUserId(), condition.getCapacity(), condition.getGameInfo().ordinal());
    }

    @Override
    public void cancel(Long userId) {
        matchingQueue.delete(userId);
    }
}
