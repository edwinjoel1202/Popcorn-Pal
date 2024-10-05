package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.repository.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin(origins = "http://localhost:5173") // Adjust the port number to your React dev server
public class movieService {

    @Autowired
    private MovieRepository movieRepository;

    private static final String TMDB_API_KEY = "ec0e9ec87809bf77dbbe98c171b19b39";
    private static final String TMDB_TRENDING_URL = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + TMDB_API_KEY;
    private static final String TMDB_MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/";

    public JsonNode getTrendingMoviesFromTMDB() {
        RestTemplate restTemplate = new RestTemplate();
        String url = TMDB_TRENDING_URL;
        JsonNode response = restTemplate.getForObject(url, JsonNode.class);
        return response != null ? response.get("results") : null;
    }

    public Optional<Movie> getMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public JsonNode getMovieDetailsFromTMDB(String tmdbId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = TMDB_MOVIE_DETAILS_URL + tmdbId + "?api_key=" + TMDB_API_KEY;
        return restTemplate.getForObject(url, JsonNode.class);
    }
}
