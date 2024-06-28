package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        return setFilmListParams(filmList);
    }

    public List<Film> getFilmsByPage(List<Film> list, int page) {
        int start = (page - 1) * 5;
        int end = Math.min(page * 5, list.size());
        return list.subList(start, end);
    }
    public List<Film> getFilmsByPage(int page) {
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Film> filmPage = filmRepository.findAll(pageRequest);
        return setFilmListParams(filmPage.getContent());
    }

    public List<Film> getFilmsByPageWithTitle(int page, String title) {
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Film> filmPage = filmRepository.findByTitleContaining(title, pageRequest);
        return setFilmListParams(filmPage.getContent());
    }

    public Film getFilmById(int id) {
        Film result = filmRepository.findById(id).orElse(null);
        if (result != null) {
            result.calculateAndSetAverageRating();
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
            //film.setUserLiked(film.getUsers().stream().anyMatch(user -> user.getUsername().equals(username)));

            film.setUserLiked(film.getRatings().stream().anyMatch(rating -> rating.getUser().getUsername().equals(username) && rating.getRatingValue() == 5));
            film.setUserDisliked(film.getRatings().stream().anyMatch(rating -> rating.getUser().getUsername().equals(username) && rating.getRatingValue() == 0));
            film.calculateAndSetAverageRating();
        });
        return filmList;
    }
    private Film setFilmParams(Film film) {
        String username = authService.getCurrentUsername();
        //film.setUserLiked(film.getUsers().stream().anyMatch(user -> user.getUsername().equals(username)));

        film.setUserLiked(film.getRatings().stream().anyMatch(rating -> rating.getUser().getUsername().equals(username) && rating.getRatingValue() == 5));
        film.setUserDisliked(film.getRatings().stream().anyMatch(rating -> rating.getUser().getUsername().equals(username) && rating.getRatingValue() == 0));
        return film;
    }
}

