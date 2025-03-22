package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Watchlist;
import com.edwin.Popcorn_Pal.repository.WatchlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class WatchlistService {

    private static final Logger logger = LoggerFactory.getLogger(WatchlistService.class);

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public Watchlist addToWatchlist(String username, Long tmdbMovieId) {
        logger.info("Adding movie ID: {} to watchlist for user: {}", tmdbMovieId, username);
        if (watchlistRepository.existsByUsernameAndTmdbMovieId(username, tmdbMovieId)) {
            logger.warn("Movie ID: {} already in watchlist for user: {}", tmdbMovieId, username);
            throw new IllegalStateException("Movie already in watchlist");
        }
        Watchlist watchlist = new Watchlist(username, tmdbMovieId);
        Watchlist savedWatchlist = watchlistRepository.save(watchlist);
        logger.debug("Movie ID: {} added to watchlist with ID: {}", tmdbMovieId, savedWatchlist.getWatchlistId());
        return savedWatchlist;
    }

    public boolean isInWatchlist(String username, Long tmdbMovieId) {
        return watchlistRepository.existsByUsernameAndTmdbMovieId(username, tmdbMovieId);
    }

    public List<Watchlist> getUserWatchlist(String username) {
        logger.info("Fetching watchlist for user: {}", username);
        List<Watchlist> watchlist = watchlistRepository.findByUsername(username);
        for (Watchlist entry : watchlist) {
            enrichWithMovieDetails(entry);
        }
        return watchlist;
    }

    public void deleteFromWatchlist(UUID watchlistId) {
        logger.info("Deleting watchlist entry with ID: {}", watchlistId);
        watchlistRepository.deleteById(watchlistId);
    }

    private void enrichWithMovieDetails(Watchlist entry) {
        try {
            String url = String.format(
                    "https://api.themoviedb.org/3/movie/%d?api_key=%s",
                    entry.getTmdbMovieId(), tmdbApiKey
            );
            logger.info("Fetching TMDB details for movie ID: {} with URL: {}", entry.getTmdbMovieId(), url);
            MovieDetails movie = restTemplate.getForObject(url, MovieDetails.class);
            if (movie != null) {
                logger.info("TMDB response - Title: {}, Poster Path: {}", movie.getTitle(), movie.getPosterPath());
                entry.setTitle(movie.getTitle());
                // Option 1: Set raw poster_path as returned by TMDB (frontend will append base URL)
                entry.setPosterPath(movie.getPosterPath());
                // Option 2: Set full URL directly (uncomment if preferred)
                // entry.setPosterPath(movie.getPosterPath() != null ? TMDB_IMAGE_BASE_URL + movie.getPosterPath() : null);
            } else {
                logger.warn("No movie details returned from TMDB for movie ID: {}", entry.getTmdbMovieId());
                entry.setTitle("Unknown Title");
                entry.setPosterPath(null);
            }
        } catch (Exception e) {
            logger.error("Error fetching TMDB details for movie ID {}: {}", entry.getTmdbMovieId(), e.getMessage());
            entry.setTitle("Unknown Title");
            entry.setPosterPath(null);
        }
    }
}

class MovieDetails {
    private String title;
    private String poster_path; // Matches TMDB API field name

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return poster_path; // Use underscore as per TMDB response
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }
}