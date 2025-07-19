package com.edwin.Popcorn_Pal.controller;

import com.edwin.Popcorn_Pal.model.Watchlist;
import com.edwin.Popcorn_Pal.service.WatchlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private static final Logger logger = LoggerFactory.getLogger(WatchlistController.class);

    @Autowired
    private WatchlistService watchlistService;

    @PostMapping
    public ResponseEntity<Watchlist> addToWatchlist(@RequestBody WatchlistRequest request) {
        logger.info("Received request to add movie ID: {} to watchlist", request.getTmdbMovieId());
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Watchlist watchlist = watchlistService.addToWatchlist(username, request.getTmdbMovieId());
            return ResponseEntity.ok(watchlist);
        } catch (IllegalStateException e) {
            logger.warn("Movie already in watchlist: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error("Error adding to watchlist: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/check/{tmdbMovieId}")
    public ResponseEntity<Boolean> isInWatchlist(@PathVariable Long tmdbMovieId) {
        logger.info("Checking if movie ID: {} is in watchlist", tmdbMovieId);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            boolean inWatchlist = watchlistService.isInWatchlist(username, tmdbMovieId);
            return ResponseEntity.ok(inWatchlist);
        } catch (Exception e) {
            logger.error("Error checking watchlist: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Watchlist>> getUserWatchlist() {
        logger.info("Received request to fetch watchlist");
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Watchlist> watchlist = watchlistService.getUserWatchlist(username);
            return ResponseEntity.ok(watchlist);
        } catch (Exception e) {
            logger.error("Error fetching watchlist: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{watchlistId}")
    public ResponseEntity<String> deleteFromWatchlist(@PathVariable UUID watchlistId) {
        logger.info("Received request to delete watchlist entry with ID: {}", watchlistId);
        try {
            watchlistService.deleteFromWatchlist(watchlistId);
            return ResponseEntity.ok("Movie removed from watchlist");
        } catch (Exception e) {
            logger.error("Error deleting from watchlist: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing movie from watchlist");
        }
    }
}

class WatchlistRequest {
    private Long tmdbMovieId;

    public Long getTmdbMovieId() {
        return tmdbMovieId;
    }

    public void setTmdbMovieId(Long tmdbMovieId) {
        this.tmdbMovieId = tmdbMovieId;
    }
}