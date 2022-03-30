package com.example.board_game.service.room;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.room.RoomRepository;
import com.example.board_game.domain.user.User;
import com.example.board_game.domain.user.UserRepository;
import com.example.board_game.dto.room.CreateOrUpdateRoomDto;
import com.example.board_game.dto.room.GetRoomHeaderDto;
import com.example.board_game.dto.room.GetRoomHeaderListDto;
import com.example.board_game.dto.room.GetRoomInfoDto;
import com.example.board_game.dto.user.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public GetRoomHeaderDto createRoom(CreateOrUpdateRoomDto dto, SessionUser user) {
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));

        Room room = new Room(dto.getName(), dto.getCapacity(), findUser);

        return new GetRoomHeaderDto(roomRepository.save(room));
    }

    public void updateRoom(CreateOrUpdateRoomDto dto, Long id, SessionUser user) {
        Room room = findOne(id);

        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        room.updateRoomInfo(dto.getName(), dto.getCapacity(), findUser);
    }

    public GetRoomHeaderListDto getRoomsList() {
        List<Room> all = roomRepository.findAll();
        List<GetRoomHeaderDto> list = new ArrayList<>();

        for (Room room : all) {
            GetRoomHeaderDto getRoomHeaderDto = new GetRoomHeaderDto(room);
            list.add(getRoomHeaderDto);
        }

        return new GetRoomHeaderListDto(list);
    }

    public GetRoomInfoDto getRoomUsers(Long roomId) {
        Room room = findOne(roomId);

        return new GetRoomInfoDto(room.getName(), room.getUsers());
    }

    public void joinRoom(Long roomId, User user) {
        Room room = findOne(roomId);

        room.join(user);
    }

    public Room findOne(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
    }

    public Game findGame(Long roomId) {
        return findOne(roomId).getGame();
    }

    public boolean startGame(Long roomId) {
        Room room = findOne(roomId);
        if(room.checkStart()){
            room.startGame();
            return true;
        } else {
            return false;
        }
    }

    public void exitRoom(Long roomId, User user) {
        Room room = findOne(roomId);
        room.exit(user);

        if(room.getNumberOfUsers() == 0){
            roomRepository.deleteById(roomId);
        }
    }
}
