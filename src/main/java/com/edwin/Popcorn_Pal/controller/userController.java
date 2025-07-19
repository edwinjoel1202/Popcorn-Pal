package com.edwin.Popcorn_Pal.controller;

import com.edwin.Popcorn_Pal.model.User;
import com.edwin.Popcorn_Pal.service.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class userController {

    private static final Logger logger = LoggerFactory.getLogger(userController.class);

    @Autowired
    private userService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        logger.info("Received request to register user: {}", user.getUsername());
        try {
            Optional<User> existingUser = userService.getUserByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                logger.warn("Username {} already taken", user.getUsername());
                return new ResponseEntity<>("Username already taken", HttpStatus.CONFLICT);
            }
            userService.saveUser(user);
            logger.info("User {} registered successfully", user.getUsername());
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage());
            return new ResponseEntity<>("Error registering user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<userService.LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Received login request for user: {}", loginRequest.getUsername());
        try {
            userService.LoginResponse response = userService.authenticateUser(
                    loginRequest.getUsername(), loginRequest.getPassword()
            );
            if (response.isSuccess()) {
                logger.info("User {} logged in successfully", loginRequest.getUsername());
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Login failed for user: {}", loginRequest.getUsername());
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("Error during login for user {}: {}", loginRequest.getUsername(), e.getMessage());
            return new ResponseEntity<>(
                    new userService.LoginResponse(false, "An error occurred during login", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        logger.info("Received request to fetch current user");
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username == null || username.isEmpty()) {
                logger.warn("No authenticated user found");
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }
            Optional<User> userOptional = userService.getUserByUsername(username);
            if (userOptional.isPresent()) {
                logger.info("Fetched details for user: {}", username);
                return ResponseEntity.ok(new UserResponse(username)); // Return only username
            } else {
                logger.warn("User not found for username: {}", username);
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching current user: {}", e.getMessage());
            return new ResponseEntity<>("Error fetching user details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("Received request to fetch user by username: {}", username);
        try {
            Optional<User> user = userService.getUserByUsername(username);
            if (user.isPresent()) {
                logger.info("User {} found", username);
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                logger.warn("User {} not found", username);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching user {}: {}", username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        logger.info("Received request to delete user with ID: {}", userId);
        try {
            userService.deleteUser(userId);
            logger.info("User with ID {} deleted successfully", userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error deleting user with ID {}: {}", userId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class UserResponse {
        private String username;

        public UserResponse(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}