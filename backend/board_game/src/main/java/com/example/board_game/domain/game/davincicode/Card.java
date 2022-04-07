package com.example.board_game.domain.game.davincicode;

import lombok.Getter;

@Getter
public class Card {
    private final CardNumber number;
    private final CardColor color;
    private boolean flipped;

    public Card(CardNumber number, CardColor color) {
        this(number, color, false);
    }

    public Card(CardNumber number, CardColor color, boolean flipped) {
        this.number = number;
        this.color = color;
        this.flipped = flipped;
    }

    public void flipped() {
        flipped = true;
    }

    public boolean compareNumber(CardNumber number) {
        return this.number.equals(number);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card) {
            Card card = (Card) obj;
            return this.number == card.number && this.color == card.color;
        } else {
            return false;
        }
    }
}
