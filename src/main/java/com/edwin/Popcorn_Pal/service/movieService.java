package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class movieService {

    private static final Logger logger = LoggerFactory.getLogger(movieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";

    public List<Object> getTrendingMovies() {
        String url = TMDB_API_BASE_URL + "/trending/movie/week?api_key=" + tmdbApiKey;
        logger.info("Fetching trending movies from TMDB with URL: {}", url);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("results")) {
                List<Object> movies = (List<Object>) response.get("results");
                logger.debug("Fetched {} trending movies from TMDB", movies.size());
                return movies;
            } else {
                logger.warn("No 'results' field in TMDB response or response is null");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Failed to fetch trending movies from TMDB: {}", e.getMessage());
            throw new RuntimeException("Error fetching trending movies: " + e.getMessage());
        }
    }

    public Object getMovieDetails(Long movieId) {
        String url = TMDB_API_BASE_URL + "/movie/" + movieId + "?api_key=" + tmdbApiKey;
        logger.info("Fetching movie details from TMDB for ID {} with URL: {}", movieId, url);
        try {
            Object movieDetails = restTemplate.getForObject(url, Object.class);
            if (movieDetails != null) {
                logger.debug("Fetched movie details for ID: {}", movieId);
                return movieDetails;
            } else {
                logger.warn("No movie details returned for ID: {}", movieId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to fetch movie details for ID {}: {}", movieId, e.getMessage());
            throw new RuntimeException("Error fetching movie details: " + e.getMessage());
        }
    }

    public Movie saveMovie(Movie movie) {
        logger.info("Saving movie with ID: {}", movie.getMovieId());
        return movieRepository.save(movie);
    }

    public List<Object> getNowPlayingMovies() {
        String url = TMDB_API_BASE_URL + "/movie/now_playing?api_key=" + tmdbApiKey;
        logger.info("Fetching now playing movies from TMDB with URL: {}", url);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("results")) {
                List<Object> movies = (List<Object>) response.get("results");
                logger.debug("Fetched {} now playing movies from TMDB", movies.size());
                return movies;
            } else {
                logger.warn("No 'results' field in TMDB response or response is null");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Failed to fetch now playing movies from TMDB: {}", e.getMessage());
            throw new RuntimeException("Error fetching now playing movies: " + e.getMessage());
        }
    }

    public List<Object> getPopularMovies() {
        String url = TMDB_API_BASE_URL + "/movie/popular?api_key=" + tmdbApiKey;
        logger.info("Fetching popular movies from TMDB with URL: {}", url);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("results")) {
                List<Object> movies = (List<Object>) response.get("results");
                logger.debug("Fetched {} popular movies from TMDB", movies.size());
                return movies;
            } else {
                logger.warn("No 'results' field in TMDB response or response is null");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Failed to fetch popular movies from TMDB: {}", e.getMessage());
            throw new RuntimeException("Error fetching popular movies: " + e.getMessage());
        }
    }

    public List<Object> getTopRatedMovies() {
        String url = TMDB_API_BASE_URL + "/movie/top_rated?api_key=" + tmdbApiKey;
        logger.info("Fetching Top Rated movies from TMDB with URL: {}", url);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("results")) {
                List<Object> movies = (List<Object>) response.get("results");
                logger.debug("Fetched {} top rated movies from TMDB", movies.size());
                return movies;
            } else {
                logger.warn("No 'results' field in TMDB response or response is null");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Failed to fetch Top rated movies from TMDB: {}", e.getMessage());
            throw new RuntimeException("Error fetching Top Rated movies: " + e.getMessage());
        }
    }

    public List<Object> getUpcomingMovies() {
        String url = TMDB_API_BASE_URL + "/movie/upcoming?api_key=" + tmdbApiKey;
        logger.info("Fetching Upcoming movies from TMDB with URL: {}", url);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("results")) {
                List<Object> movies = (List<Object>) response.get("results");
                logger.debug("Fetched {} upcoming movies from TMDB", movies.size());
                return movies;
            } else {
                logger.warn("No 'results' field in TMDB response or response is null");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Failed to fetch Upcoming movies from TMDB: {}", e.getMessage());
            throw new RuntimeException("Error fetching Upcoming movies: " + e.getMessage());
        }
    }
}