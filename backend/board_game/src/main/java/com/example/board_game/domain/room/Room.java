package com.example.board_game.domain.room;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Room {
    private Long id;
    private String name;
    private int capacity;

    private User owner;
    private List<User> users = new ArrayList<>();

    private Game game;

    @Builder
    public Room(String name, int capacity, User owner) {
        this.name = name;
        this.capacity = capacity;
        this.owner = owner;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public void changeOwner(User newOwner) {
        this.owner = newOwner;
    }

    public void changeOwner() {
        for (User user : users) {
            if(!checkOwner(user)){
                changeOwner(user);
                break;
            }
        }
    }

    public boolean checkOwner(User user) {
        return owner.equals(user);
    }

    public void updateRoomInfo(String name, int capacity, User user) {
        if(!checkOwner(user))
            throw new IllegalArgumentException("방의 주인이 아닙니다");
        this.name = name;
        this.capacity = capacity;
    }

    public void join(User user) {
        users.add(user);
    }

    public void exit(User user) {
        if(checkOwner(user)) {
            changeOwner();
        } else {
            users.remove(user);
        }
    }

    public int getNumberOfUsers() {
        return users.size();
    }
}

