package com.example.board_game.domain.user;

import com.example.board_game.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    protected User() {
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof User)) return false;
        if(obj == this) return true;

        return ((User) obj).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nickname);
    }
}
