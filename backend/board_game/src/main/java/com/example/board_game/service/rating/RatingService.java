package com.example.board_game.service.rating;

import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.rating.Rating;
import com.example.board_game.domain.rating.RatingRepository;
import com.example.board_game.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public void addRating(User user, GameInfo gameInfo, int addRating) {
        Rating rating = findRating(user, gameInfo);

        rating.updateRating(addRating);
    }

    public Rating findRating(User user, GameInfo gameInfo) {
        return ratingRepository.findByUserIdAndGameInfo(user.getId(), gameInfo)
                .orElse(ratingRepository.save(new Rating(user, gameInfo)));
    }
}
