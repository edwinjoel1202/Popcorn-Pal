/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.controller;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.service.movieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author edwin
 */

@RestController
@RequestMapping("/api/movies")
public class movieController {

    @Autowired
    private movieService movieService;

    // Get top 20 trending movies
    @GetMapping("/trending")
    public ResponseEntity<List<Movie>> getTrendingMovies() {
        List<Movie> trendingMovies = movieService. getTrendingMoviesFromTMDB();
        return new ResponseEntity<>(trendingMovies, HttpStatus.OK);
    }

    // Get movie by title
    @GetMapping("/title/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {
        Movie movie = movieService.getMovieByTitle(title);
        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
