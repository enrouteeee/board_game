package com.example.board_game.service.matching;

import com.example.board_game.auth.UserDto;
import com.example.board_game.domain.user.User;
import com.example.board_game.dto.matching.MatchingCommand;
import com.example.board_game.dto.matching.MatchingInfo;
import com.example.board_game.dto.room.CreateOrUpdateRoomDto;
import com.example.board_game.dto.room.GetRoomHeaderDto;
import com.example.board_game.service.matching.system.MatchingSystem;
import com.example.board_game.service.matching.system.MatchingSystemType;
import com.example.board_game.service.room.RoomService;
import com.example.board_game.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class MatchingService {

    private final Map<String, MatchingSystem> matchingSystemMap;
    private final RoomService roomService;
    private final UserService userService;

    private final Map<Long, String> uuMap = new ConcurrentHashMap<>();   // <userId, UUid>

    public void uUidUserIdMapping(Long userId, String UUid) {
        uuMap.put(userId, UUid);
    }

    public void deleteMapping(Long userId) {
        uuMap.remove(userId);
    }

    public MatchingInfo matchingStart(MatchingCommand command) {
        MatchingSystem matchingSystem;
        if(command.getMatchingSystemType() == MatchingSystemType.FIFO) {
            matchingSystem = matchingSystemMap.get("FIFOMatchingSystem");
        } else if (command.getMatchingSystemType() == MatchingSystemType.RATING) {
            matchingSystem = matchingSystemMap.get("RatingMatchingSystem");
        } else {
            throw new IllegalArgumentException("잘못된 타입의 매칭시스템입니다.");
        }

        List<Long> userIds = matchingSystem.enqueue(command.toCondition());
        if(userIds == null){
            return null;
        }

        User user = userService.findUserById(userIds.get(0));
        GetRoomHeaderDto room = roomService.createRoom(new CreateOrUpdateRoomDto("", userIds.size()), new UserDto(user));

        for (Long userId : userIds) {
            // 방에 입장
            User u = userService.findUserById(userId);
            roomService.joinRoom(room.getId(), u);

            // {uuid, userId} 맵에서 삭제
            uuMap.remove(userId);
        }

        return new MatchingInfo(room.getId(), userIds);
    }

    public void matchingCancel(Long userId) {
        for (String key : matchingSystemMap.keySet()) {
            matchingSystemMap.get(key).cancel(userId);
        }
    }
}
