package com.edwin.Popcorn_Pal.repository;

import com.edwin.Popcorn_Pal.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {
    boolean existsByUsernameAndTmdbMovieId(String username, Long tmdbMovieId);
    List<Watchlist> findByUsername(String username);
}