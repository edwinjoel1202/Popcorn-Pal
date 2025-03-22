package com.edwin.Popcorn_Pal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Spring Security filter chain");
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    logger.info("Setting up authorization rules");
                    auth
                            .requestMatchers("/api/users/register").permitAll()
                            .requestMatchers("/api/users/login").permitAll()
                            .requestMatchers("/api/movies/trending").permitAll()
                            .requestMatchers("/api/movies/details/**").permitAll()
                            .requestMatchers("/api/reviews/movie/**").permitAll()
                            .requestMatchers("/api/movies/now-playing").permitAll()
                            .requestMatchers("/api/movies/popular").permitAll()
                            .requestMatchers("/api/movies/top-rated").permitAll()
                            .requestMatchers("/api/movies/upcoming").permitAll()
                            .requestMatchers("/api/reviews/movie/**").permitAll()
                            .requestMatchers("/api/reviews").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .anyRequest().authenticated();
                });
        SecurityFilterChain chain = http.build();
        logger.info("Security filter chain configured with permitAll for /api/movies/trending");
        return chain;
    }
}
