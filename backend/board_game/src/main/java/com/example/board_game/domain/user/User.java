package com.example.board_game.domain.user;

import com.example.board_game.domain.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    protected User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
