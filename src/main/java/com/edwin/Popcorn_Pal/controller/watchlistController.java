/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.controller;

import com.edwin.Popcorn_Pal.model.Watchlist;
import com.edwin.Popcorn_Pal.service.watchlistService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author edwin
 */
@RestController
@RequestMapping("/api/watchlist")
public class watchlistController {

    @Autowired
    private watchlistService watchlistService;

    // Fetch the watchlist for a specific user
    @GetMapping("/{userId}")
    public ResponseEntity<?> getWatchlistByUserId(@PathVariable UUID userId) {
        List<Watchlist> watchlist = watchlistService.getWatchlistByUserId(userId);
        if (watchlist != null && !watchlist.isEmpty()) {
            return ResponseEntity.ok(watchlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a movie to a user's watchlist
    @PostMapping("/{userId}/add/{movieId}")
    public ResponseEntity<?> addMovieToWatchlist(@PathVariable UUID userId, @PathVariable Long movieId) {
        Watchlist watchlistItem = watchlistService.saveWatchlistItem(userId, movieId);
        if (watchlistItem != null) {
            return ResponseEntity.ok(watchlistItem);
        } else {
            return ResponseEntity.badRequest().body("Invalid user or movie ID");
        }
    }

    // Delete a movie from the watchlist
    @DeleteMapping("/delete/{watchlistId}")
    public ResponseEntity<?> deleteWatchlistItem(@PathVariable UUID watchlistId) {
        watchlistService.deleteWatchlistItem(watchlistId);
        return ResponseEntity.ok("Watchlist item deleted");
    }
}
