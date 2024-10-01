package com.edwin.Popcorn_Pal.controller;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.service.movieService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class movieController {

    @Autowired
    private movieService movieService;

    // Get top 20 trending movies
    @GetMapping("/trending")
    public ResponseEntity<JsonNode> getTrendingMovies() {
        JsonNode trendingMovies = movieService.getTrendingMoviesFromTMDB();
        return new ResponseEntity<>(trendingMovies, HttpStatus.OK);
    }

    // Get movie by title
    @GetMapping("/title/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        Optional<Movie> movieOptional = movieService.getMovieByTitle(title);
        if (movieOptional.isPresent()) {
            return new ResponseEntity<>(movieOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get movie details by TMDB ID
    @GetMapping("/details/{tmdbId}")
    public ResponseEntity<JsonNode> getMovieDetailsByTMDBId(@PathVariable String tmdbId) {
        JsonNode movieDetails = movieService.getMovieDetailsFromTMDB(tmdbId);
        if (movieDetails != null) {
            return new ResponseEntity<>(movieDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
