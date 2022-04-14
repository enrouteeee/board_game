package com.example.board_game.domain.game.davincicode;

import com.example.board_game.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Player {
    private Long userId;
    private String nickname;
    private List<Card> cards = new ArrayList<>();
    private Card selectCard;
    private PlayerState state;

    private int lastAddedCardIndex;

    public Player(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        state = PlayerState.PLAYING;
    }

    public void selectCard(Card card) {
        this.selectCard = card;
    }

    public void selectCardPosition(Integer playerCardIdx, Card card) {
        if(selectCard.equals(card)) {
            cards.add(playerCardIdx, card);
            this.lastAddedCardIndex = playerCardIdx;
        } else {
            throw new IllegalArgumentException("카드가 맞지 않습니다.");
        }
    }

    /*
    카드 순서가 맞는지 확인
     */
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
        return cards.get(index).compareNumber(cardNumber);
    }

    public Long getId(){
        return this.userId;
    }

    public void flipCard(int index) {
        cards.get(index).flipped();

        if(checkOut()){
            this.state = PlayerState.OUT;
        }
    }

    public void flipLastCard() {
        flipCard(this.lastAddedCardIndex);
    }

    public boolean checkOut() {
        for (Card card : cards) {
            if (!card.isFlipped()) {
                return false;
            }
        }
        return true;
    }

    public void AllFlip() {
        for (Card card : cards) {
            card.flipped();
        }
        this.state = PlayerState.OUT;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Player)) return false;
        if(obj == this) return true;

        return ((Player) obj).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, nickname);
    }
}
