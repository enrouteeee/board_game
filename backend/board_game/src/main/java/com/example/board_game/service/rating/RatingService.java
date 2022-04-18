package com.example.board_game.service.rating;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.game.LeaderBoard;
import com.example.board_game.domain.rating.Rating;
import com.example.board_game.domain.rating.RatingRepository;
import com.example.board_game.domain.user.User;
import com.example.board_game.observer.game.GameObserver;
import com.example.board_game.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class RatingService implements GameObserver {

    private final RatingRepository ratingRepository;
    private final UserService userService;

    /*
    이긴 상대와의 rating 차이 만큼을 더하고
    진 상대와의 rating 차이의 반비례 만큼을 빼서
    평균을 구해 나의 rating 에 업데이트 한다

    대략적인. 추후 수정
     */
    public void updateRating(LeaderBoard leaderBoard) {
        Long[] board = leaderBoard.getBoard();

        List<Rating> ratings = new ArrayList<>();

        int maxScore = 120;
        int minScore = 14;
        int standardRating = 240;
        double modScore = 2.6;

        for (Long uid : board) {
            Rating rating = findRating(uid, leaderBoard.getGameInfo());
            ratings.add(rating);
        }

        for (int i=0; i<ratings.size(); i++) {
            int updateRating = 0;
            for(int j=0; j<ratings.size(); j++) {
                if(i > j) {
                    int iRating = ratings.get(i).getRating();
                    int jRating = ratings.get(j).getRating();

                    int subRating = jRating - iRating;
                    if(subRating > standardRating) {
                        updateRating += maxScore;
                    } else if(subRating > standardRating * 0.8) {
                        updateRating += maxScore * 0.8;
                    } else if(subRating > standardRating * 0.6) {
                        updateRating += maxScore * 0.6;
                    } else if(subRating > standardRating * 0.4) {
                        updateRating += maxScore * 0.4;
                    } else if(subRating > standardRating * 0.2) {
                        updateRating += maxScore * 0.2;
                    } else {
                        updateRating += minScore;
                    }
                    updateRating *= 1.5;
                } else if (i < j) {
                    int iRating = ratings.get(i).getRating();
                    int jRating = ratings.get(j).getRating();

                    int subRating = jRating - iRating;
                    if(subRating > standardRating) {
                        updateRating -= minScore;
                    } else if(subRating > standardRating * 0.8) {
                        updateRating -= maxScore * 0.2;
                    } else if(subRating > standardRating * 0.6) {
                        updateRating -= maxScore * 0.4;
                    } else if(subRating > standardRating * 0.4) {
                        updateRating -= maxScore * 0.6;
                    } else if(subRating > standardRating * 0.2) {
                        updateRating -= maxScore * 0.8;
                    } else {
                        updateRating -= maxScore;
                    }
                }
                updateRating /= modScore;
            }
            updateRating /= (ratings.size() -1);
            ratings.get(i).updateRating(updateRating);
        }
    }

    public Rating findRating(Long userId, GameInfo gameInfo) {
        User user = userService.findUserById(userId);

        Rating rating = ratingRepository.findByUserAndGameInfo(user, gameInfo)
                .orElse(new Rating(user, gameInfo));

        return ratingRepository.save(rating);
    }

    @Override
    public void gameFinished(Game game) {
        updateRating(game.getLeaderBoard());
    }
}
