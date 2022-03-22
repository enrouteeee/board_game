package com.example.board_game.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetRoomHeaderListDto {
    private List<GetRoomHeaderDto> list = new ArrayList<>();
}
