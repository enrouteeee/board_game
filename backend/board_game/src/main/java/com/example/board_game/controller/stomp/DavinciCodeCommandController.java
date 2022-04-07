package com.example.board_game.controller.stomp;

import com.example.board_game.domain.user.User;
import com.example.board_game.dto.game.davinciCode.DavinciCodeCommand;
import com.example.board_game.service.game.DavinciCodeService;
import com.example.board_game.service.room.RoomService;
import com.example.board_game.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class DavinciCodeCommandController {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final RoomService roomService;
    private final DavinciCodeService davinciCodeService;

    @MessageMapping("/game")
    public void sendCommand(DavinciCodeCommand command) {
        User user = userService.findUserById(command.getUserId());

        switch (command.getType()) {
            case SELECT_CARD:
                System.out.println("보드에서 카드를 선택함");
                davinciCodeService.selectCard(command);
                break;
            case SELECT_CARD_POSITION:
                System.out.println("카드 위치를 선택함");
                davinciCodeService.selectCardPosition(command);
                break;
            case PREDICT_CARD:
                System.out.println("카드를 예측함");
                davinciCodeService.predictCard(command);
                break;
            case PASS_TURN:
                System.out.println("차례를 넘김");
                break;
            case EXIT:
                System.out.println("게임에서 나감");
                roomService.exitRoom(command.getGameId(), user);
                if(roomService.getRoomPlaying(command.getGameId())){
                    davinciCodeService.exitGame(command);
                }
                break;
        }

        template.convertAndSend("/sub/game/" + command.getGameId(), command);
        System.out.println("브로드캐스트");
    }
}
