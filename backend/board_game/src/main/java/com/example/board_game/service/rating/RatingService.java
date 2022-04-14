package com.example.board_game.service.rating;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.game.LeaderBoard;
import com.example.board_game.domain.rating.Rating;
import com.example.board_game.domain.rating.RatingRepository;
import com.example.board_game.domain.user.User;
import com.example.board_game.observer.game.GameObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RatingService implements GameObserver {

    private final RatingRepository ratingRepository;

    public void updateRating(LeaderBoard standing) {
        System.out.println("standing = " + standing);
    }

    public Rating findRating(User user, GameInfo gameInfo) {
        return ratingRepository.findByUserIdAndGameInfo(user.getId(), gameInfo)
                .orElse(ratingRepository.save(new Rating(user, gameInfo)));
    }

    @Override
    public void update(Game game) {
        updateRating(game.getLeaderBoard());
    }
}
