package com.example.board_game.auth.token;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Token generateToken(String uid, String role) {
        long tokenPeriod = 1000L * 60L * 60L * 3L;
        return new Token(uid, role, new Date(new Date().getTime() + tokenPeriod), key);
    }

    public Token generateRefreshToken(String uid, String role) {
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;
        return new Token(uid, role, new Date(new Date().getTime() + refreshPeriod), key);
    }

    public String getUid(String token) {
        Token t = strToToken(token);
        return t.getUid();
    }

    public boolean verifyToken(String token) {
        Token t = strToToken(token);

        return t.validate();
    }

    public Token strToToken(String token) {
        return new Token(token, key);
    }
}
