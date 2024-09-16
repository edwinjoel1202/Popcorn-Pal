package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Movie;
import com.edwin.Popcorn_Pal.repository.MovieRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing movies.
 *
 * @author edwin
 */
@Service
@Transactional
public class movieService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Fetches all movies from the database.
     *
     * @return List of movies.
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Fetches a movie by its ID.
     *
     * @param movieId The UUID of the movie to find.
     * @return An Optional containing the movie if found, or empty if not found.
     */
    public Optional<Movie> getMovieById(UUID movieId) {
        return movieRepository.findById(movieId);
    }

    /**
     * Saves a movie to the database.
     *
     * @param movie The movie to be saved.
     * @return The saved movie entity.
     */
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Deletes a movie from the database.
     *
     * @param movieId The UUID of the movie to be deleted.
     */
    public void deleteMovie(UUID movieId) {
        movieRepository.deleteById(movieId);
    }
}
