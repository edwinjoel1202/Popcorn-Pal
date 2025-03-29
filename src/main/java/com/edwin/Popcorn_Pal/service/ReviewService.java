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
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String N8N_WEBHOOK_URL = "http://localhost:5678/webhook-test/d0409d23-feb9-4b2b-b3e9-b193ab896810";

    // Save a new review
    public Review saveReview(Review review) {
        logger.info("Saving review for movie ID: {} by user: {}", review.getTmdbMovieId(), review.getUsername());
        // Ensure createdAt is set if not provided
        if (review.getCreatedAt() == null) {
            review.setCreatedAt(LocalDateTime.now());
        }

        // Call the n8n webhook to get sentiment analysis
        String sentiment = getSentimentAnalysis(review.getReviewText());
        review.setTag(sentiment);

        Review savedReview = reviewRepository.save(review);
        logger.debug("Review saved with ID: {} and sentiment tag: {}", savedReview.getReviewId(), savedReview.getTag());
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

    // New method to get a review by ID
    public Review getReviewById(UUID reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    // New method to delete a review
    public void deleteReview(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // Method to call n8n webhook and get sentiment analysis
    private String getSentimentAnalysis(String reviewText) {
        try {
            // Prepare the payload for the webhook
            Map<String, String> payload = Map.of("text", reviewText);
            logger.info("Sending review text to n8n webhook: {}", reviewText);

            // Call the webhook
            List<Map<String, Object>> response = restTemplate.postForObject(N8N_WEBHOOK_URL, payload, List.class);

            // Parse the response
            if (response != null && !response.isEmpty()) {
                Map<String, Object> result = response.get(0);
                Map<String, String> sentimentAnalysis = (Map<String, String>) result.get("sentimentAnalysis");
                String category = sentimentAnalysis.get("category");
                logger.info("Received sentiment analysis: {}", category);
                return category; // Returns "Positive", "Negative", or "Neutral"
            } else {
                logger.warn("Empty or invalid response from n8n webhook");
                return "Neutral"; // Default to Neutral if webhook fails
            }
        } catch (Exception e) {
            logger.error("Error calling n8n webhook: {}", e.getMessage());
            return "Neutral"; // Default to Neutral on error
        }
    }
}