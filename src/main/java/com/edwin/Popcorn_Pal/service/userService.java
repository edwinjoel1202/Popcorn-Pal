/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.User;
import com.edwin.Popcorn_Pal.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author edwin
 */

@Service
@Transactional
public class userService {

    @Autowired
    private UserRepository userRepository;

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find user by ID
    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    // Find user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Register a new user
    public User saveUser(User user) {
        // Password is saved as plain text for now
        return userRepository.save(user);
    }

    // Delete a user
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    // Authenticate user based on username and password
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user; // Authentication successful
        }
        return Optional.empty(); // Authentication failed
    }
}
