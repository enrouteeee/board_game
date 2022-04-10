package com.example.board_game.controller.stomp;

import com.example.board_game.domain.user.User;
import com.example.board_game.dto.room.RoomCommand;
import com.example.board_game.service.room.RoomService;
import com.example.board_game.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class RoomCommandController {

    private final SimpMessagingTemplate template;
    private final RoomService roomService;
    private final UserService userService;

    @MessageMapping("/room")
    public void sendCommand(RoomCommand command) {
        User user = userService.findUserById(command.getUserId());

        switch (command.getType()) {
            case ENTER:
                System.out.println("방에 입장함");
                roomService.joinRoom(command.getRoomId(), user);
                break;
            case EXIT:
                System.out.println("방에서 나감");
                roomService.exitRoom(command.getRoomId(), user);
                break;
            case CHAT:
                // 채팅 관리 로직
                System.out.println("채팅보냄");
                break;
            case START:
                if(roomService.startGame(command.getRoomId())){
                    command.setMessage("게임시작");
                } else {
                    command.setMessage("게임시작불가능");
                }
                break;
        }
        template.convertAndSend("/sub/room/" + command.getRoomId(), command);
        System.out.println("브로드캐스트");
    }
}