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

    @Autowired
    private AuthService authService;

    public List<Film> getAllFilms() {
        List<Film> filmList = filmRepository.findAll();
        // remove genres from each film to avoid circular references
        filmList.forEach(film -> {
            film.setGenres(null);
        });
        return setFilmListParams(filmList);
    }

    public List<Film> getFilmsByPage(List<Film> list, int page) {
        int start = (page - 1) * 5;
        int end = Math.min(page * 5, list.size());
        return list.subList(start, end);
    }

    public List<Film> getFilmsWithTitle(String title) {
        return setFilmListParams( filmRepository.findByTitleContaining(title) );
    }

    public Film getFilmById(int id) {
        Film result = filmRepository.findById(id).orElse(null);
        if (result != null) {
            result.setGenres(null);
            return setFilmParams(result);
        }
        return null;
    }

    public Film saveFilm(Film film) {
        return setFilmParams(filmRepository.save(film));
    }

    public void deleteFilm(int id) {
        filmRepository.deleteById(id);
    }

    private List<Film> setFilmListParams(List<Film> filmList) {
        String username = authService.getCurrentUsername();
        filmList.forEach(film -> {
            film.setUserLiked(film.getUsers().stream().anyMatch(user -> user.getUsername().equals(username)));
        });
        return filmList;
    }
    private Film setFilmParams(Film film) {
        String username = authService.getCurrentUsername();
        film.setUserLiked(film.getUsers().stream().anyMatch(user -> user.getUsername().equals(username)));
        return film;
    }
}

