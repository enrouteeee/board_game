package com.example.board_game.domain.room;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.game.davincicode.DavinciCode;
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

    private boolean playing;    //게임 중인지 아닌지

    @Builder
    public Room(String name, int capacity, User owner) {
        this.name = name;
        this.capacity = capacity;
        this.owner = owner;
        this.playing = false;
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

    private boolean checkOwner(User user) {
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
        }
        users.remove(user);
    }

    public int getNumberOfUsers() {
        return users.size();
    }

    public boolean checkStart() {
        /*
        더미 데이터
         */
//        if(game == null){
//            throw new IllegalArgumentException("게임이 정해지지 않았습니다.");
//        }
        setGame(new DavinciCode(this.id, getNumberOfUsers()));

        return this.game.checkStart();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void startGame(){
        this.playing = true;
        this.game.start(users);
    }
}

