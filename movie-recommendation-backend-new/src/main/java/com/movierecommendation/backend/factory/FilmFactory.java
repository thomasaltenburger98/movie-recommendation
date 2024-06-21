package com.movierecommendation.backend.factory;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.Genre;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilmFactory {
    public Film createFromRecord(CSVRecord filmRecord, Map<String, Genre> genreMap) {
        Film film = new Film();
        film.setId(Long.parseLong(filmRecord.get("movieId")));
        film.setTitle(filmRecord.get("title"));
        String[] genreNames = filmRecord.get(2).split("\\|");
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
        film.setGenres(genres);
        return film;
    }
}
