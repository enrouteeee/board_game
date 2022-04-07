package com.example.board_game.dto.game.davinciCode;

import com.example.board_game.domain.game.davincicode.Card;
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
public class CardDto {
    private String number;
    private String color;
    private boolean flipped;

    public Card toCard() {
        CardNumber cardNumber = DavinciCodeCommand.stringToCardNumber(number);
        CardColor cardColor = DavinciCodeCommand.stringToCardColor(color);

        return new Card(cardNumber, cardColor);
    }
}
