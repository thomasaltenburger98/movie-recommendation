package com.movierecommendation.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.movierecommendation.backend.helpers.TmdbConfig;
import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.model.data.FilmDetail;
import com.movierecommendation.backend.model.data.WatchProviders;
import com.movierecommendation.backend.model.views.FilmViews;
import com.movierecommendation.backend.service.FilmService;
import com.movierecommendation.backend.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private ImdbService imdbService;

    @JsonView(FilmViews.FilmView.class)
    @GetMapping
    public List<Film> index() {
        return filmService.getAllFilms();
    }

    @JsonView(FilmViews.FilmViewWithGenres.class)
    @GetMapping("/{id}")
    public Film show(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    // pagination
    @JsonView(FilmViews.FilmView.class)
    @GetMapping("/page/{page}")
    public List<Film> showPage(@PathVariable int page, @RequestParam(required = false,name = "search") String title) {
        if (title != null && !title.isEmpty()) {
            return filmService.getFilmsByPageWithTitle(page, title);
        }
        List<Film> filmsByPage = filmService.getFilmsByPage(page);
        return filmsByPage;
    }

    @JsonView(FilmViews.FilmView.class)
    @PostMapping
    public Film store(@RequestBody Film film) {
        //film.setErscheinungsjahr(new Date());
        return filmService.saveFilm(film);
    }

    @JsonView(FilmViews.FilmView.class)
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
        String baseUrlOriginal = imdbConfig.getImages().getBaseUrl() + imdbConfig.getImages().getPosterSizes().getLast();
        filmDetail.setImage_url(baseUrl + filmDetail.getPoster_path());
        filmDetail.setImage_url_original(baseUrlOriginal + filmDetail.getPoster_path());
        return filmDetail;
    }

    @GetMapping("/providers/{tmdb_id}")
    public WatchProviders getFilmProviders(@PathVariable Long tmdb_id) {
        return imdbService.getFilmProvidersFromTmdb(tmdb_id);
    }
}