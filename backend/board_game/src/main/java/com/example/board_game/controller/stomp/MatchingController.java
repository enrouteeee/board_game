package com.example.board_game.controller.stomp;

import com.example.board_game.dto.matching.MatchingCommand;
import com.example.board_game.dto.matching.MatchingInfo;
import com.example.board_game.service.matching.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class MatchingController {

    private final SimpMessagingTemplate template;
    private final MatchingService matchingService;

    @MessageMapping("/matching")
    public void sendCommand(MatchingCommand command, Principal principal) {

        switch (command.getType()) {
            case QUEUE:
                System.out.println("매칭시작");
                matchingService.uUidUserIdMapping(command.getUserId(), principal.getName());
                MatchingInfo matchingInfo = matchingService.matchingStart(command);
                if(matchingInfo == null)
                    return;
                for (String uuId : matchingInfo.getUuIds()) {
                    template.convertAndSendToUser(uuId, "/sub/matching", matchingInfo.getRoomId());
                }
                break;
            case CANCEL:
                System.out.println("매칭취소");
                matchingService.deleteMapping(command.getUserId());
                matchingService.matchingCancel(command.getUserId());
                break;
        }
    }
}
