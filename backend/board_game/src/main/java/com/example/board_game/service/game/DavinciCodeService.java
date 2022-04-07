package com.example.board_game.service.game;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.game.davincicode.DavinciCode;
import com.example.board_game.dto.game.davinciCode.DavinciCodeCommand;
import com.example.board_game.dto.game.davinciCode.GetGameInfoDto;
import com.example.board_game.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DavinciCodeService {
    private final RoomService roomService;

    public GetGameInfoDto getGameInfo(Long id) {
        DavinciCode game = findGame(id);

        return new GetGameInfoDto(game);
    }

    public void selectCard(DavinciCodeCommand command) {
        DavinciCode game = findGame(command.getGameId());

    }

    private DavinciCode findGame(Long id) {
        Game game = roomService.findGame(id);

        if(game instanceof DavinciCode) {
            return (DavinciCode) game;
        } else {
            throw new IllegalArgumentException("게임의 종류가 다릅니다.");
        }
    }
}
