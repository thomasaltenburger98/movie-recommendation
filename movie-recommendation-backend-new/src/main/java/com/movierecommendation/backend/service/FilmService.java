package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getAllFilms() {
        List<Film> filmList = filmRepository.findAll();
        // remove genres from each film to avoid circular references
        filmList.forEach(film -> {
            film.setGenres(null);
        });
        return filmList;
    }

    public List<Film> getFilmsByPage(int page) {
        List<Film> allFilms = this.getAllFilms();
        int start = (page - 1) * 5;
        int end = Math.min(page * 5, allFilms.size());
        return allFilms.subList(start, end);
    }

    public Film getFilmById(int id) {
        return filmRepository.findById(id).orElse(null);
    }

    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    public void deleteFilm(int id) {
        filmRepository.deleteById(id);
    }
}

