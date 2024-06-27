package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.model.data.RecommendationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RecommendationService {
    private static final String MACHINE_LEARNING_SERVICE_URL = "http://localhost:5000/recommend?user_id={userid}&num_recommendations=20&page={page}&per_page={perpage}";

    @Autowired
    private FilmService filmService;

    public List<Film> getRecommendedFilms(List<Rating> ratedFilms, int userId, int page, int perPage) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RecommendationResult> result = restTemplate.exchange(MACHINE_LEARNING_SERVICE_URL, HttpMethod.GET, null, RecommendationResult.class, userId, page, perPage);

        if (result.getStatusCode().is2xxSuccessful()) {
            return getFilmsFromRecommendationResult(Objects.requireNonNull(result.getBody()));
        } else {
            throw new RuntimeException("Failed to fetch recommendations from machine learning service");
        }
    }

    private List<Film> getFilmsFromRecommendationResult(RecommendationResult result) {
        List<Film> films = new ArrayList<>();
        ArrayList<Long[]> recommendations = result.getResult();
        for (Long[] recommendation : recommendations) {
            Film film = filmService.getFilmById(recommendation[0].intValue());
            if (film != null) {
                films.add(film);
            }
        }
        return films;
    }

}