package com.example.board_game.controller.api;

import com.example.board_game.auth.UserDto;
import com.example.board_game.dto.room.CreateOrUpdateRoomDto;
import com.example.board_game.dto.room.GetRoomHeaderDto;
import com.example.board_game.dto.room.GetRoomHeaderListDto;
import com.example.board_game.dto.room.GetRoomInfoDto;
import com.example.board_game.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<GetRoomHeaderDto> createRoom(@RequestBody CreateOrUpdateRoomDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();

        return ResponseEntity.ok(roomService.createRoom(dto, user));
    }

    @GetMapping
    public ResponseEntity<GetRoomHeaderListDto> getRooms(){

        return ResponseEntity.ok(roomService.getRoomsList());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<GetRoomInfoDto> getRoomInfo(@PathVariable("roomId") Long id) {

        return ResponseEntity.ok(roomService.getRoomUsers(id));
    }

    @GetMapping("/{roomId}/enter")
    public ResponseEntity<Boolean> checkAbleToEnter(@PathVariable("roomId") long id) {

        return ResponseEntity.ok(roomService.checkAbleToEnter(id));
    }

}