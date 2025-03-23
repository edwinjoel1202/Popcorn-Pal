package com.edwin.Popcorn_Pal.controller;

import com.edwin.Popcorn_Pal.service.movieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173") // Adjust port if your frontend uses a different one
public class movieController {

    private static final Logger logger = LoggerFactory.getLogger(movieController.class);

    @Autowired
    private movieService movieService;

    @GetMapping("/trending")
    public ResponseEntity<List<Object>> getTrendingMovies() {
        logger.info("Received request for trending movies");
        try {
            List<Object> trendingMovies = movieService.getTrendingMovies();
            logger.debug("Returning {} trending movies", trendingMovies.size());
            return ResponseEntity.ok(trendingMovies);
        } catch (Exception e) {
            logger.error("Error fetching trending movies: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/details/{movieId}")
    public ResponseEntity<Object> getMovieDetails(@PathVariable Long movieId) {
        logger.info("Received request for movie details with ID: {}", movieId);
        try {
            Object movieDetails = movieService.getMovieDetails(movieId);
            logger.debug("Returning movie details for ID: {}", movieId);
            return ResponseEntity.ok(movieDetails);
        } catch (Exception e) {
            logger.error("Error fetching movie details for ID {}: {}", movieId, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/now-playing")
    public ResponseEntity<List<Object>> getNowPlayingMovies() {
        logger.info("Received request for now playing movies");
        try {
            List<Object> nowPlayingMovies = movieService.getNowPlayingMovies();
            logger.debug("Returning {} now playing movies", nowPlayingMovies.size());
            return ResponseEntity.ok(nowPlayingMovies);
        } catch (Exception e) {
            logger.error("Error fetching now playing movies: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Object>> getPopularMovies() {
        logger.info("Received request for popular movies");
        try {
            List<Object> popularMovies = movieService.getPopularMovies();
            logger.debug("Returning {} popular movies", popularMovies.size());
            return ResponseEntity.ok(popularMovies);
        } catch (Exception e) {
            logger.error("Error fetching popular movies: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Object>> getTopRatedMovies() {
        logger.info("Received request for Top Rated movies");
        try {
            List<Object> topRatedMovies = movieService.getTopRatedMovies();
            logger.debug("Returning {} top rated movies", topRatedMovies.size()); // Fixed log message
            return ResponseEntity.ok(topRatedMovies);
        } catch (Exception e) {
            logger.error("Error fetching Top Rated movies: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Object>> getUpcomingMovies() {
        logger.info("Received request for Upcoming movies");
        try {
            List<Object> upcomingMovies = movieService.getUpcomingMovies();
            logger.debug("Returning {} upcoming movies", upcomingMovies.size());
            return ResponseEntity.ok(upcomingMovies);
        } catch (Exception e) {
            logger.error("Error fetching Upcoming movies: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    // New search endpoint
    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchMovies(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        logger.info("Received search request for query: {}", query);
        try {
            List<Map<String, Object>> searchResults = movieService.searchMovies(query, page);
            logger.debug("Returning {} search results for query: {}", searchResults.size(), query);
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            logger.error("Error fetching search results for query {}: {}", query, e.getMessage());
            return ResponseEntity.status(500).body(List.of());
        }
    }
}