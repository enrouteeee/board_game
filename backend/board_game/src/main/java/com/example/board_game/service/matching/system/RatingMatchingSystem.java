package com.example.board_game.service.matching.system;

import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.matching.MatchingQueue;
import com.example.board_game.domain.rating.Rating;
import com.example.board_game.service.matching.MatchingCondition;
import com.example.board_game.service.rating.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RatingMatchingSystem implements MatchingSystem {

    private final RatingService ratingService;
    private final int MaxRatingIndex = 14;

    private MatchingQueue matchingQueue;

    @PostConstruct
    void init() {
        matchingQueue = new MatchingQueue(6, GameInfo.values().length, MaxRatingIndex);
    }

    @Override
    public List<Long> enqueue(MatchingCondition condition) {
        Rating rating = ratingService.findRating(condition.getUserId(), condition.getGameInfo());

        return matchingQueue.
                enQueue(condition.getUserId(), condition.getCapacity(), ratingToInt(rating));
    }

    @Override
    public void cancel(Long userId) {
        matchingQueue.delete(userId);
    }

    public int ratingToInt(Rating rating) {
        int result = rating.getRating()/100 - 6;
        if(result > MaxRatingIndex-1) {
            result = MaxRatingIndex-1;
        } else if(result < 0) {
            result = 0;
        }

        return result;
    }
}
