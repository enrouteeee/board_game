package com.example.board_game.service.room;

import com.example.board_game.auth.UserDto;
import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.room.RoomRepository;
import com.example.board_game.domain.user.User;
import com.example.board_game.domain.user.UserRepository;
import com.example.board_game.dto.room.CreateOrUpdateRoomDto;
import com.example.board_game.dto.room.GetRoomHeaderDto;
import com.example.board_game.dto.room.GetRoomHeaderListDto;
import com.example.board_game.dto.room.GetRoomInfoDto;
import com.example.board_game.service.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final GameService gameService;

    public GetRoomHeaderDto createRoom(CreateOrUpdateRoomDto dto, UserDto user) {
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));

        Room room = new Room(dto.getName(), dto.getCapacity(), findUser);

        return new GetRoomHeaderDto(roomRepository.save(room));
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

        return new GetRoomInfoDto(room);
    }

    public boolean getRoomPlaying(Long roomId) {
        Room room = findOne(roomId);

        return room.isPlaying();
    }

    public boolean checkAbleToEnter(Long roomId) {
        Room room = findOne(roomId);

        return room.isAbleToEnter();
    }

    public void joinRoom(Long roomId, User user) {
        Room room = findOne(roomId);

        room.join(user);
    }

    private Room findOne(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
    }

    public boolean startGame(Long roomId) {
        Room room = findOne(roomId);
        if(room.checkStart()){
            room.startGame();
            gameService.createGame(room.getGameType(), room.getUsers(), room);
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
