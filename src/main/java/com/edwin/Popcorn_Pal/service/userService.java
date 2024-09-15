/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.model.User;
import com.edwin.Popcorn_Pal.repository.MovieRepository;
import com.edwin.Popcorn_Pal.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author edwin
 */
public class userService {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private MovieRepository movieRepo;
    
    public User getUserById(UUID id) {
        return userRepo.findById(id).orElse(null);
    }
    
    public User saveUser(User user) {
        return userRepo.save(user);
    }
    
    public List<Movie> getWatchlist(UUID userId) {
        return userRepo.findById(userId).map(User :: getWatchlist).orElse(null);
    }
    
    public void addMovieToWatchlist(UUID userId, Integer movieId) {
        
        Optional<User> user = userRepo.findById(userId);
        Optional<Movie> movie = movieRepo.findById(movieId);
        
        if(user.isPresent() && movie.isPresent()) {
            User u = user.get();
            u.getWatchlist().add(movie.get());
            userRepo.save(u);
        } 
    }
    
}
