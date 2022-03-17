package com.example.board_game.domain.user;

import com.example.board_game.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected User() {
    }

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            User user = (User) obj;
            return user.getId().equals(this.id);
        } else {
            return false;
        }
    }
}
