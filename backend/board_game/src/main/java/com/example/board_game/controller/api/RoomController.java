package com.example.board_game.controller.api;

import com.example.board_game.dto.room.CreateOrUpdateRoomDto;
import com.example.board_game.dto.room.GetRoomHeaderDto;
import com.example.board_game.dto.room.GetRoomHeaderListDto;
import com.example.board_game.dto.room.GetRoomInfoDto;
import com.example.board_game.dto.user.SessionUser;
import com.example.board_game.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<GetRoomHeaderDto> createRoom(@RequestBody CreateOrUpdateRoomDto dto, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");

        return ResponseEntity.ok(roomService.createRoom(dto, user));
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

    @GetMapping("/{roomId}")
    public ResponseEntity<GetRoomInfoDto> getRoomInfo(@PathVariable("roomId") Long id) {

        return ResponseEntity.ok(roomService.getRoomUsers(id));
    }
}