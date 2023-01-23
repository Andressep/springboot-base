package com.example.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class Config {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/productos/**")
                            .allowedOrigins("http://localhost:8080")
                            .allowedMethods("GET", "POST", "PUT", "DELETE")
                            .maxAge(3600);
            }
        };
    }

}
