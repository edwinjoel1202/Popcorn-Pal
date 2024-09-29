package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.repository.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class movieService {

    @Autowired
    private MovieRepository movieRepository;

    // TMDB API URL for fetching trending movies
    private final String TMDB_API_URL = "https://api.themoviedb.org/3/trending/movie/day?api_key=3d7ff50f7da5073a45fdf0e8da92e059";

    // Method to fetch trending movies from TMDB
    public JsonNode getTrendingMoviesFromTMDB() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(TMDB_API_URL, JsonNode.class);
    }

    // Method to get a movie by its title
    public Optional<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
}
