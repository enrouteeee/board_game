package com.example.board_game.controller.api.game;

import com.example.board_game.dto.game.davinciCode.GetGameInfoDto;
import com.example.board_game.service.game.DavinciCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game/davinci")
public class DavinciCodeController {

    private final DavinciCodeService davinciCodeService;

    @GetMapping("/{gameId}")
    public ResponseEntity<GetGameInfoDto> getGameInfo(@PathVariable("gameId") Long id) {

        return ResponseEntity.ok(davinciCodeService.getGameInfo(id));
    }
}
