package com.example.board_game.service.game;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.game.GameFactory;
import com.example.board_game.domain.game.GameRepository;
import com.example.board_game.domain.game.GameType;
import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {
    private final GameRepository gameRepository;

    public Long createGame(GameType gameType, List<User> users, Room room) {
        Game game = GameFactory.createGame(gameType, users, room);
        gameRepository.save(game);

        return game.getId();
    }

    public Game findOne(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
    }
}

