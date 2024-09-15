/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.repository.MovieRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 *
 * @author edwin
 */

@Service
public class movieService {
    
    @Autowired
    private MovieRepository movieRepo;
    
    // Method to get all the movies from the Movie Entity..:)
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }
    
    //Method to get a specific movie with its id..:)
    public Movie getMovieById(Integer id) {
        return movieRepo.findById(id).orElse(null);
    }
    
    public Movie saveMovie(Movie movie){
        return movieRepo.save(movie);
    }
    
    
    public List<Movie> getMoviesFromTMDB() {
        // the logic to fetch from TMDB API here
        return null;
    }
    
    
}
