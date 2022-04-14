package com.example.board_game.domain.game.davincicode;

import lombok.Getter;

import java.util.Objects;

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
        if(obj == null) return false;
        if(!(obj instanceof Card)) return false;
        if(obj == this) return true;

        Card card = (Card) obj;
        return card.number == this.number && card.color == this.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, color);
    }
}
