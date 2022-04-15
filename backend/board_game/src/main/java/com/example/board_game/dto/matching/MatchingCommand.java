package com.example.board_game.dto.matching;

import com.example.board_game.domain.game.GameInfo;
import com.example.board_game.service.matching.MatchingCondition;
import com.example.board_game.service.matching.system.MatchingSystemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchingCommand {
    public enum MatchingCommandType {
        QUEUE, CANCEL
    }

    private Long userId;
    private MatchingCommandType type;
    private GameInfo gameInfo;
    private MatchingSystemType matchingSystemType;
    private Integer capacity;

    public MatchingCondition toCondition() {
        return new MatchingCondition(userId, gameInfo, capacity);
    }
}
