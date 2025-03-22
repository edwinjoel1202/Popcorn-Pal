package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Watchlist;
import com.edwin.Popcorn_Pal.repository.WatchlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistService {

    private static final Logger logger = LoggerFactory.getLogger(WatchlistService.class);

    @Autowired
    private WatchlistRepository watchlistRepository;

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
}