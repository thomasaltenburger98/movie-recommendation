package com.movierecommendation.backend.controller;

import com.movierecommendation.backend.helpers.TmdbConfig;
import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.FilmDetail;
import com.movierecommendation.backend.service.FilmService;
import com.movierecommendation.backend.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private ImdbService imdbService;

    @GetMapping
    public List<Film> index() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film show(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    // pagination
    @GetMapping("/page/{page}")
    public List<Film> showPage(@PathVariable int page, @RequestParam(required = false,name = "search") String title) {
        if (title != null && !title.isEmpty()) {
            return filmService.getFilmsByPage(filmService.getFilmsWithTitle(title), page);
        }
        List<Film> filmsByPage = filmService.getFilmsByPage(filmService.getAllFilms(), page);
        return filmsByPage;
    }

    @PostMapping
    public Film store(@RequestBody Film film) {
        //film.setErscheinungsjahr(new Date());
        return filmService.saveFilm(film);
    }

    @PutMapping("/{id}")
    public Film update(@RequestBody Film film, @PathVariable int id) {
        Film existingFilm = filmService.getFilmById(id);
        if (existingFilm != null) {
            existingFilm.setTitle(film.getTitle());
            //existingFilm.setBeschreibung(film.getBeschreibung());
            return filmService.saveFilm(existingFilm);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        filmService.deleteFilm(id);
    }

    @GetMapping("/detail/{id}")
    public FilmDetail getFilmDetails(@PathVariable int id) {
        // get tmdb film id from film id
        Film film = filmService.getFilmById(id);
        if (film == null) {
            return null;
        }
        Long tmdbId = film.getTmdbId();

        FilmDetail filmDetail = imdbService.getFilmInfoFromTmdb(tmdbId);
        // set image url
        TmdbConfig imdbConfig = imdbService.getTmdbConfig();
        String baseUrl = imdbConfig.getImages().getBaseUrl() + imdbConfig.getImages().getPosterSizes().getFirst();
        filmDetail.setImage_url(baseUrl + filmDetail.getPoster_path());
        return filmDetail;
    }
}