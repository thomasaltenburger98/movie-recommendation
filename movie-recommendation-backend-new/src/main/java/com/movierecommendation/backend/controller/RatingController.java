package com.movierecommendation.backend.controller;

import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.repository.RatingRepository;
import com.movierecommendation.backend.service.AuthService;
import com.movierecommendation.backend.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    // TODO use service instead of repository
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private AuthService authService;

    @GetMapping
    public List<Rating> index() {
        return ratingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Rating show(@PathVariable Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Rating store(@RequestBody Map<String, String> body) {
        String filmId = body.get("film_id");
        String ratingValue = body.get("rating_value");
        Rating rating = null;

        // TODO: Get user id and add to rating

        // Save the rating
        return ratingRepository.save(rating);
    }

    @PutMapping("/{id}")
    public Rating update(@RequestBody Rating rating, @PathVariable Long id) {
        Rating existingRating = ratingRepository.findById(id).orElse(null);
        if (existingRating != null) {
            existingRating.setRatingValue(rating.getRatingValue());
            return ratingRepository.save(existingRating);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable Long id) {
        ratingRepository.deleteById(id);
    }
}