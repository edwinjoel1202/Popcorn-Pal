package com.edwin.Popcorn_Pal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "watchlist")
public class Watchlist {

    @Id
    @GeneratedValue
    private UUID watchlistId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "tmdb_movie_id", nullable = false)
    private Long tmdbMovieId;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    // Constructors
    public Watchlist() {
    }

    public Watchlist(String username, Long tmdbMovieId) {
        this.username = username;
        this.tmdbMovieId = tmdbMovieId;
        this.addedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(UUID watchlistId) {
        this.watchlistId = watchlistId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTmdbMovieId() {
        return tmdbMovieId;
    }

    public void setTmdbMovieId(Long tmdbMovieId) {
        this.tmdbMovieId = tmdbMovieId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}