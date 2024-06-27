package com.movierecommendation.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.model.views.GenreViews;
import com.movierecommendation.backend.repository.FilmRepository;
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
    private RatingService ratingService;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private AuthService authService;

    @JsonView(GenreViews.GenreView.class)
    @GetMapping
    public List<Rating> index() {
        return ratingRepository.findAll();
    }

    @JsonView(GenreViews.GenreView.class)
    @GetMapping("/{id}")
    public Rating show(@PathVariable Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Map<String, String> store(@RequestBody Map<String, String> body) {
        String filmId = body.get("film_id");
        String ratingValue = body.get("rating_value");
        User user = authService.getUserByUsername(authService.getCurrentUsername());
        Film film = filmRepository.findById(Integer.parseInt(filmId)).orElse(null);

        // delete existing rating if it exists
        ratingService.deleteRatingByUserIdAndFilmId(user.getId(), Long.parseLong(filmId));

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setFilm(film);
        rating.setRatingValue(Integer.parseInt(ratingValue));

        ratingService.saveRating(rating);
        return Map.of("message", "Rating created successfully");
    }

    @JsonView(GenreViews.GenreView.class)
    @PutMapping("/{id}")
    public Rating update(@RequestBody Rating rating, @PathVariable Long id) {
        Rating existingRating = ratingRepository.findById(id).orElse(null);
        if (existingRating != null) {
            existingRating.setRatingValue(rating.getRatingValue());
            return ratingService.saveRating(existingRating);
        }
        return null;
    }

    @PostMapping("/delete")
    public void destroy(@RequestBody Map<String, String> body) {
        long userId = authService.getUserIdByUsername(authService.getCurrentUsername());
        long filmId = Long.parseLong(body.get("film_id"));
        float ratingValue = Float.parseFloat(body.get("rating_value"));
        ratingService.deleteRatingByUserIdAndFilmIdAndRatingValue(userId, filmId, ratingValue);
    }

    @GetMapping("/count")
    public long getRatingCount() {
        return ratingService.getRatingCount(authService.getCurrentUsername());
    }
}