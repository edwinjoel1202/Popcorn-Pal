/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.model.User;
import com.edwin.Popcorn_Pal.model.Watchlist;
import com.edwin.Popcorn_Pal.repository.MovieRepository;
import com.edwin.Popcorn_Pal.repository.UserRepository;
import jakarta.transaction.Transactional;
import com.edwin.Popcorn_Pal.repository.WatchlistRepository;
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
public class watchlistService {

   @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    // Fetch all watchlist items for a specific user
    public List<Watchlist> getWatchlistByUserId(UUID userId) {
        return watchlistRepository.findByUser_UserId(userId);
    }

    // Save a new watchlist item (user adds a movie to their watchlist)
    public Watchlist saveWatchlistItem(UUID userId, UUID movieId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (user.isPresent() && movie.isPresent()) {
            Watchlist watchlist = new Watchlist();
            watchlist.setUser(user.get());
            watchlist.setMovie(movie.get());
            return watchlistRepository.save(watchlist);
        } else {
            return null; // You could throw an exception here for invalid user or movie
        }
    }

    // Delete a watchlist item
    public void deleteWatchlistItem(UUID watchlistId) {
        watchlistRepository.deleteById(watchlistId);
    }
}
