package com.example.board_game.domain.game.davincicode;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Board {
    private List<Card> cards = new Vector<>();

    public void init() {
        CardNumber[] cardNumbers = CardNumber.values();
        CardColor[] cardColors = CardColor.values();
        for (CardNumber cardNumber : cardNumbers) {
            for (CardColor cardColor : cardColors) {
                this.cards.add(new Card(cardNumber, cardColor));
            }
        }
        shuffle(this.cards);
    }

    private void shuffle(List<Card> lists) {
        for(int i=0; i<lists.size(); i++){
            int j = (int)(Math.random()* lists.size());
            Collections.swap(lists, i, j);
        }
    }

    public Card selected(Card card, int idx) {
        Card selectedCard;
        if(cards.get(idx).equals(card)) {
            selectedCard = cards.get(idx);
        } else {
            throw new IllegalArgumentException("해당 카드가 존재하지 않습니다.");
        }
        cards.remove(card);

        return selectedCard;
    }

    public List<Card> getCards() {
        return cards;
    }
}
