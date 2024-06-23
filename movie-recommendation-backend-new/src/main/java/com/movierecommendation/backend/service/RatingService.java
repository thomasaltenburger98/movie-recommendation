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
    @Autowired
    private CSVDataManager csvDataManager;

    public void rateFilm(int filmId, int userId, int ratingValue) {
        Rating rating = new Rating();
        //rating.setFilm(new Film(filmId));
        //rating.setUser(new User(userId));
        rating.setRatingValue(ratingValue);
        rating.setErstelltAm(new Date());
        ratingRepository.save(rating);
    }

    public Rating saveRating(Rating rating) {
        Rating savedRating = ratingRepository.save(rating);
        csvDataManager.writeRatingToCSV(savedRating);
        return savedRating;
    }
    public void deleteRatingById(long id) {
        // TODO delete rating from CSV file
        ratingRepository.deleteById(id);
    }

    public List<Rating> getAllRatedFilmOfUser(int userId) {
        return ratingRepository.findAllByUserId(userId);
    }
    public List<Rating> getAllRatedFilmOfUser(String username) {
        return ratingRepository.findAllByUserUsername(username);
    }

    public List<Rating> getPositiveRatedFilmOfUser(int userId) {
        // TODO use sql query instead of stream
        return this.getAllRatedFilmOfUser(userId).stream().filter(rating -> rating.getRatingValue() > 3).toList();
    }
    public List<Rating> getPositiveRatedFilmOfUser(String username) {
        // TODO use sql query instead of stream
        return this.getAllRatedFilmOfUser(username).stream().filter(rating -> rating.getRatingValue() > 3).toList();
    }

    public long getRatingCount(int userId) {
        return getAllRatedFilmOfUser(userId).size();
    }
    public long getRatingCount(String username) {
        return getAllRatedFilmOfUser(username).size();
    }
}

