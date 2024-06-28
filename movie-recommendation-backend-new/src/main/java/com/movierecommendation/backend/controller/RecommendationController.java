package com.movierecommendation.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.model.views.GenreViews;
import com.movierecommendation.backend.service.AuthService;
import com.movierecommendation.backend.service.RatingService;
import com.movierecommendation.backend.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AuthService authService;

    @GetMapping
    public List<Film> getRecommendationsForCurrentUser(@RequestParam(required = false, name = "page") Integer page) {
        if (page == null) {
            page = 1;
        }
        List<Rating> ratedFilms = ratingService.getPositiveRatedFilmOfUser(authService.getCurrentUsername());
        int userId = authService.getUserIdByUsername(authService.getCurrentUsername()).intValue();
        List<Film> list = recommendationService.getRecommendedFilms(ratedFilms, userId, page, 5);
        return list;
    }
}