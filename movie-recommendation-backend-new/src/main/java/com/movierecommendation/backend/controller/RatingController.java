package com.movierecommendation.backend.controller;

import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping
    public List<Rating> index() {
        return ratingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Rating show(@PathVariable Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Rating store(@RequestBody Rating rating) {
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