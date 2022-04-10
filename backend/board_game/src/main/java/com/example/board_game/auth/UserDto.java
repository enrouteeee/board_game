package com.example.board_game.auth;

import com.example.board_game.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements Serializable {

    private String email;

    public UserDto(User user) {
        this.email = user.getEmail();
    }

    public UserDto(String email) {
        this.email = email;
    }
}