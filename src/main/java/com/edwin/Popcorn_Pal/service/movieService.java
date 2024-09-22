package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for managing movies.
 *
 * @author edwin
 */

@Service
public class movieService {

    private final String apiKey = "3d7ff50f7da5073a45fdf0e8da92e059"; // Replace with your TMDB API key
    private final String TMDB_API_URL = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate = new RestTemplate();

    // Get trending movies from TMDB
    public List<Movie> getTrendingMoviesFromTMDB() {
        String url = TMDB_API_URL + "/trending/movie/day?api_key=" + apiKey;
        JsonNode response = restTemplate.getForObject(url, JsonNode.class);

        List<Movie> trendingMovies = new ArrayList<>();
        if (response != null && response.has("results")) {
            for (JsonNode movieData : response.get("results")) {
                Movie movie = new Movie();
                movie.setTitle(movieData.get("title").asText());
                movie.setPosterUrl("https://image.tmdb.org/t/p/w500" + movieData.get("poster_path").asText());
                movie.setMovieId(UUID.randomUUID()); // Generate a unique ID
                trendingMovies.add(movie);
            }
        }
        return trendingMovies;
    }

    // Get movie by title from TMDB
    public Movie getMovieByTitle(String title) {
        String url = TMDB_API_URL + "/search/movie?query=" + title + "&api_key=" + apiKey;
        JsonNode response = restTemplate.getForObject(url, JsonNode.class);

        if (response != null && response.has("results") && response.get("results").size() > 0) {
            JsonNode movieData = response.get("results").get(0); // Get the first movie result
            Movie movie = new Movie();
            movie.setTitle(movieData.get("title").asText());
            movie.setDescription(movieData.get("overview").asText());
            movie.setPosterUrl("https://image.tmdb.org/t/p/w500" + movieData.get("poster_path").asText());
            movie.setMovieId(UUID.randomUUID());
            return movie;
        }
        return null;
    }
}
