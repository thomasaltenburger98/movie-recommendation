package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    // This method simulates communication with the machine learning service
    public List<Film> getRecommendedFilms(List<Rating> ratedFilms) {
        // TODO: Replace this with actual communication with the machine learning service
        // For now, we just return the same list as a placeholder


        return ratedFilms.stream().map(Rating::getFilm).toList();
    }
}