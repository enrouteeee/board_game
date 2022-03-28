package com.example.board_game.controller.stomp;

import com.example.board_game.domain.game.davincicode.DavinciCode;
import com.example.board_game.dto.game.DavinciCodeCommand;
import com.example.board_game.dto.user.SessionUser;
import com.example.board_game.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class GameDavinciCommandController {

    private final SimpMessagingTemplate template;
    private final RoomService roomService;

    @MessageMapping("/command/game")
    public void sendCommand(DavinciCodeCommand command, HttpSession httpSession) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        DavinciCode game = (DavinciCode) roomService.findGame(command.getRoomId());

        switch (command.getType()) {
            case GET_BOARD:
                template.convertAndSend("/sub/room/" + command.getRoomId(), game.getCards());
                break;
            case GET_ORDER:
                template.convertAndSend("/sub/room/" + command.getRoomId(), game.getOrder());
                break;
            case SELECT_CARD:
            case PREDICT_CARD:
            case CHAT:
                template.convertAndSend("/sub/room/" + command.getRoomId(), command);
                break;
        }
    }
}
