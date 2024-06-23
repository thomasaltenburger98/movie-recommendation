package com.movierecommendation.backend.controller;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Rating;
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
    public List<Film> getRecommendationsForCurrentUser() {
        System.out.println("RecommendationController.getRecommendationsForCurrentUser");
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //int userId = authService.getUserIdByUsername(authService.getCurrentUsername()).intValue();
        //System.out.println("userId: " + userId);
        List<Rating> ratedFilms = ratingService.getPositiveRatedFilmOfUser(authService.getCurrentUsername());
        System.out.println("ratedFilms: " + ratedFilms.size());
        List<Film> list = recommendationService.getRecommendedFilms(ratedFilms);
        System.out.println("list: " + list.size());
        return list;
    }
}