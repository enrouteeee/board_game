package com.example.board_game.config.stomp;

import java.security.Principal;

public class StompUser implements Principal {

    private String uuid;

    public StompUser(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return this.uuid;
    }
}
