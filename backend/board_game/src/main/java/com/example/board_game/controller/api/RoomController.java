package com.example.board_game.controller.api;

import com.example.board_game.dto.room.CreateOrUpdateRoomDto;
import com.example.board_game.dto.room.GetRoomHeaderListDto;
import com.example.board_game.dto.user.SessionUser;
import com.example.board_game.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController("/api/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity createRoom(@RequestBody CreateOrUpdateRoomDto dto, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        roomService.createRoom(dto, user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{roomId}")
    public ResponseEntity updateRoom(@RequestBody CreateOrUpdateRoomDto dto, @PathVariable("roomId") Long id,
                                             HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        roomService.updateRoom(dto, id, user);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<GetRoomHeaderListDto> rooms(){

        return ResponseEntity.ok(roomService.getRoomsList());
    }
}