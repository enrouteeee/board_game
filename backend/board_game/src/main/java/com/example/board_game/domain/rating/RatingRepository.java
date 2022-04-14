package com.example.board_game.domain.rating;

import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndGameInfo(User user, GameInfo gameInfo);
}
