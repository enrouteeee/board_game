package com.example.board_game.controller.stomp;

import com.example.board_game.config.auth.dto.SessionUser;
import com.example.board_game.dto.RoomCommand;
import com.example.board_game.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class RoomCommandController {

    private final SimpMessagingTemplate template;
    private final RoomService roomService;

    @MessageMapping("/command/room")
    public void sendCommand(RoomCommand message, HttpSession httpSession) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        switch (message.getType()) {
            case ENTER:
                roomService.joinRoom(message.getRoomId(), user);
                message.setMessage(message.getSender()+"님이 방에 입장하셨습니다.");
                break;
            case EXIT:
                roomService.exitRoom(message.getRoomId(), user);
                message.setMessage(message.getSender()+"님이 방에서 퇴장하셨습니다.");
                break;
            case CHAT:
                // 채팅 관리 로직
                break;
            case START:
                if(roomService.findOne(message.getRoomId()).checkStart()){
                    message.setMessage("게임이 시작됩니다.");
                } else {
                    message.setMessage("게임을 시작할 수 없습니다.");
                }
                break;
        }
        template.convertAndSend("/sub/room/" + message.getRoomId(), message);
    }
}