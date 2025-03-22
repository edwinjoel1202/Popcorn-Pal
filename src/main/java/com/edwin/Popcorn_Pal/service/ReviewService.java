package com.edwin.Popcorn_Pal.service;

import com.edwin.Popcorn_Pal.model.Review;
import com.edwin.Popcorn_Pal.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    // Save a new review
    public Review saveReview(Review review) {
        logger.info("Saving review for movie ID: {} by user: {}", review.getTmdbMovieId(), review.getUsername());
        // Ensure createdAt is set if not provided
        if (review.getCreatedAt() == null) {
            review.setCreatedAt(LocalDateTime.now());
        }
        Review savedReview = reviewRepository.save(review);
        logger.debug("Review saved with ID: {}", savedReview.getReviewId());
        return savedReview;
    }

    // Fetch reviews for a movie with pagination
    public List<Review> getReviewsByMovieId(Long tmdbMovieId, int page, int size) {
        logger.info("Fetching reviews for movie ID: {}, page: {}, size: {}", tmdbMovieId, page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<Review> reviews = reviewRepository.findByTmdbMovieId(tmdbMovieId, pageable);
        logger.debug("Fetched {} reviews for movie ID: {}", reviews.size(), tmdbMovieId);
        return reviews;
    }
}