package com.edwin.Popcorn_Pal.repository;

import com.edwin.Popcorn_Pal.model.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    // Fetch reviews by TMDB movie ID with pagination
    List<Review> findByTmdbMovieId(Long tmdbMovieId, Pageable pageable);
}