package com.example.board_game.auth;

import com.example.board_game.auth.token.Token;
import com.example.board_game.auth.token.TokenService;
import com.example.board_game.config.UriConst;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String uid = (String) oAuth2User.getAttribute("email");
        Token accessToken = tokenService.generateToken(uid, "USER");
        Token refreshToken = tokenService.generateRefreshToken(uid, "USER");

        response.addHeader("Auth", accessToken.getToken());
        response.addHeader("Refresh", refreshToken.getToken());

        String targetUrl = UriComponentsBuilder.fromUriString(UriConst.CLIENT_URI +"/oauth/redirect")
                        .queryParam("token", accessToken.getToken()).build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
