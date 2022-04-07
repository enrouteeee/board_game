package com.example.board_game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("https://stately-pie-1e1d8b.netlify.app/")
                .allowedOrigins("http://localhost:3000/")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
