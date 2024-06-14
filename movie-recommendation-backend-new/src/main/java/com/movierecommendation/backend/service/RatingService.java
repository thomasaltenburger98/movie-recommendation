package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Rating;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public void rateFilm(int filmId, int userId, int ratingValue) {
        Rating rating = new Rating();
        //rating.setFilm(new Film(filmId));
        //rating.setUser(new User(userId));
        rating.setRatingValue(ratingValue);
        rating.setErstelltAm(new Date());
        ratingRepository.save(rating);
    }

    public List<Rating> getAllRatedFilmOfUser(int userId) {
        return ratingRepository.findAllByUserId(userId);
    }

    public long getRatingCount() {
        return ratingRepository.count();
    }
}

