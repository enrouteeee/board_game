package com.example.board_game.dto.game.davinciCode;

import com.example.board_game.domain.game.davincicode.CardColor;
import com.example.board_game.domain.game.davincicode.CardNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DavinciCodeCommand {
    public enum DavinciCodeCommandType {
        SELECT_CARD, SELECT_CARD_POSITION, PREDICT_CARD, PASS_TURN, EXIT
    }

    private Long userId;
    private DavinciCodeCommandType type;
    private Long gameId;

    private CardDto card;       // select_card, select_card_position
    private Integer boardIdx;      // select_card
    private Integer playerCardIdx; // select_card_position, predict_card
    private Integer playerIdx;     // predict_card
    private String predictNum;    // predict_card

    public static CardNumber stringToCardNumber(String number) {
        CardNumber cardNumber;
        switch (number) {
            case "0":
                cardNumber = CardNumber.ZERO;
                break;
            case "1":
                cardNumber = CardNumber.ONE;
                break;
            case "2":
                cardNumber = CardNumber.TWO;
                break;
            case "3":
                cardNumber = CardNumber.THREE;
                break;
            case "4":
                cardNumber = CardNumber.FOUR;
                break;
            case "5":
                cardNumber = CardNumber.FIVE;
                break;
            case "6":
                cardNumber = CardNumber.SIX;
                break;
            case "7":
                cardNumber = CardNumber.SEVEN;
                break;
            case "8":
                cardNumber = CardNumber.EIGHT;
                break;
            case "9":
                cardNumber = CardNumber.NINE;
                break;
            case "10":
                cardNumber = CardNumber.TEN;
                break;
            case "11":
                cardNumber = CardNumber.ELEVEN;
                break;
            case "-":
                cardNumber = CardNumber.JOKER;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }

        return cardNumber;
    }

    public static CardColor stringToCardColor(String color) {
        CardColor cardColor;
        switch (color) {
            case "black":
                cardColor = CardColor.BLACK;
                break;
            case "white":
                cardColor = CardColor.WHITE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + color);
        }

        return cardColor;
    }
}