package com.movierecommendation.backend.factory;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Genre;
import com.movierecommendation.backend.model.Rating;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RatingFactory {
    /*public Film createFromRecord(CSVRecord ratingRecord, Map<String, Genre> genreMap) {
        Rating rating = new Rating();
        rating.set
        rating.setTitle(ratingRecord.get("title"));
        String[] genreNames = ratingRecord.get(2).split("\\|");
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genreNames) {
            if (genreMap.containsKey(genreName)) {
                genres.add(genreMap.get(genreName));
            } else {
                Genre genre = new Genre();
                genre.setName(genreName);
                genres.add(genre);
            }
        }
        rating.setGenres(genres);
        return rating;
    }*/
}
