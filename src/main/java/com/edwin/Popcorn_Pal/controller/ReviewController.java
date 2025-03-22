package com.edwin.Popcorn_Pal.controller;

import com.edwin.Popcorn_Pal.model.Review;
import com.edwin.Popcorn_Pal.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:5173") // Allow CORS for frontend
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    // Save a new review
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        logger.info("Received request to add review for movie ID: {}", review.getTmdbMovieId());
        try {
            Review savedReview = reviewService.saveReview(review);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            logger.error("Error adding review: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // Fetch reviews for a movie with pagination
    @GetMapping("/movie/{tmdbMovieId}")
    public ResponseEntity<List<Review>> getReviewsByMovieId(
            @PathVariable Long tmdbMovieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Received request to fetch reviews for movie ID: {}, page: {}, size: {}", tmdbMovieId, page, size);
        try {
            List<Review> reviews = reviewService.getReviewsByMovieId(tmdbMovieId, page, size);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            logger.error("Error fetching reviews for movie ID {}: {}", tmdbMovieId, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}