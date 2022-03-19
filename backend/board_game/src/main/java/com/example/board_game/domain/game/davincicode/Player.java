package com.example.board_game.domain.game.davincicode;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private List<Card> cards = new ArrayList<>();

    private PlayerState state;

    private int lastAddedCardIndex;

    public Player(int id) {
        this.id = id;
        state = PlayerState.PLAYING;
    }

    // 카드 추가 - 기본적으로 순서를 맞춰서
    public void addCard(Card card) {
        int index=0;
        for(; index< cards.size(); index++){
            if(card.getNumber().ordinal() > cards.get(index).getNumber().ordinal()) {
                break;
            } else if (card.getNumber().ordinal() == cards.get(index).getNumber().ordinal()) {
                if (card.getColor() == CardColor.WHITE) {
                    index++;
                }
                break;
            }
        }
        cards.add(index, card);
        this.lastAddedCardIndex = index;
    }

    // 카드 추가 - 패에 조커가 있는 경우 위치를 지정해서
    public void addCard(Card card, int index){
        cards.add(index, card);
        int tmp = this.lastAddedCardIndex;
        this.lastAddedCardIndex = index;

        boolean flag = checkCardOrder();
        if(flag) {
            cards.remove(index);
            this.lastAddedCardIndex = tmp;
            throw new IllegalArgumentException("카드를 놓을 수 없는 자리 입니다.");
        }
    }

    private boolean checkCardOrder() {
        boolean flag = true;

        for(int i=1; i<cards.size(); i++){
            if(cards.get(i).getNumber() == CardNumber.JOKER || cards.get(i-1).getNumber() == CardNumber.JOKER) {
                continue;
            }

            if(cards.get(i-1).getNumber().ordinal() == cards.get(i).getNumber().ordinal()){
                if(cards.get(i-1).getColor() == CardColor.WHITE || cards.get(i).getColor() == CardColor.BLACK) {
                    flag = false;
                }
            } else if(cards.get(i-1).getNumber().ordinal() > cards.get(i).getNumber().ordinal()){
                flag = false;
            }
        }

        return flag;
    }

    public boolean checkCardNumber(CardNumber cardNumber, int index) {
        return cards.get(index).getNumber().equals(cardNumber);
    }

    public int getId(){
        return this.id;
    }

    public int getNumberOfCard() {
        return cards.size();
    }

    public int getLastAddedCardIndex() {
        return lastAddedCardIndex;
    }

    public void flipCard(int index) {
        cards.get(index).flipped();

        if(checkOut()){
            this.state = PlayerState.OUT;
        }
    }

    public boolean checkOut() {
        for (Card card : cards) {
            if (!card.isFlipped()) {
                return false;
            }
        }
        return true;
    }

    public PlayerState getState() {
        return state;
    }
}
