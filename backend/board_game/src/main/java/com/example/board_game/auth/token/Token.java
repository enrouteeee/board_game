package com.example.board_game.auth.token;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Key;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class Token {
    private final String token;
    private final Key key;

    public Token(String sub, String role, Date expiry, Key key) {
        this.key = key;
        this.token = createToken(sub, expiry, role);
    }

    private String createToken(String sub, Date expiry, String role) {
        return Jwts.builder()
                .setSubject(sub)
                .claim("role", role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid.");
        }
        return null;
    }

    public String getUid() {
        return getTokenClaims().getSubject();
    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }
}
