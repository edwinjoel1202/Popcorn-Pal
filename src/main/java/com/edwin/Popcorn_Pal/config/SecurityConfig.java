package com.edwin.Popcorn_Pal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter; // JWT filter to validate tokens

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
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (since you're using JWT)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS globally
                .authorizeHttpRequests(auth -> {
                    logger.info("Setting up authorization rules");
                    auth
                            .requestMatchers("/api/users/register").permitAll()
                            .requestMatchers("/api/users/login").permitAll()
                            .requestMatchers("/api/users/me").authenticated() // Require auth for /me
                            .requestMatchers("/api/movies/trending").permitAll()
                            .requestMatchers("/api/movies/details/**").permitAll()
                            .requestMatchers("/api/reviews/movie/**").permitAll()
                            .requestMatchers("/api/movies/now-playing").permitAll()
                            .requestMatchers("/api/movies/popular").permitAll()
                            .requestMatchers("/api/movies/top-rated").permitAll()
                            .requestMatchers("/api/movies/upcoming").permitAll()
                            .requestMatchers("/api/reviews").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        SecurityFilterChain chain = http.build();
        logger.info("Security filter chain configured with permitAll for /api/movies/trending");
        return chain;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // React frontend origin
        configuration.addAllowedMethod("*"); // Allow all HTTP methods
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.setAllowCredentials(true); // Allow auth headers (Bearer token)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}