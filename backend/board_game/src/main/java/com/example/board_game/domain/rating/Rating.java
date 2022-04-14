package com.example.board_game.domain.rating;

import com.example.board_game.domain.BaseTimeEntity;
import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"user_id", "gameInfo"})})
@Entity
public class Rating extends BaseTimeEntity {

    private static final int BaseRating = 1000;

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

    public void updateRating(int updateRating) {
        this.rating += updateRating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", user=" + user +
                ", gameInfo=" + gameInfo +
                '}';
    }
}
