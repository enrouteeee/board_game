package com.example.board_game.domain.rating;

import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Rating {

    private static final int BaseRating = 800;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private GameInfo gameInfo;

    protected Rating() {
    }

    public Rating(User user, GameInfo gameInfo) {
        this.user = user;
        this.gameInfo = gameInfo;
        this.rating = BaseRating;
    }

    public void updateRating(int addRating) {
        this.rating += addRating;
    }
}
