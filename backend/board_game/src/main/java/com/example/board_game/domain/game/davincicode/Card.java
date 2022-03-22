package com.example.board_game.domain.game.davincicode;

import lombok.Getter;

@Getter
public class Card {
    private final CardNumber number;
    private final CardColor color;
    private boolean flipped;

    public Card(CardNumber number, CardColor color) {
        this.number = number;
        this.color = color;
        flipped = false;
    }

    public void flipped() {
        flipped = true;
    }

    public boolean checkCard(CardNumber number) {
        return this.number.equals(number);
    }
}
