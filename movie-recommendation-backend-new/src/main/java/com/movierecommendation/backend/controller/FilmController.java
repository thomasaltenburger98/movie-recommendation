package com.movierecommendation.backend.controller;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping
    public List<Film> index() {
        return filmRepository.findAll();
    }

    @GetMapping("/{id}")
    public Film show(@PathVariable int id) {
        return filmRepository.findById(id).orElse(null);
    }

    // pagination
    @GetMapping("/page/{page}")
    public List<Film> showPage(@PathVariable int page) {
        System.out.println("Page: " + page);
        List<Film> allFilms = filmRepository.findAll();
        // exclude genres and users
        // TODO find better way
        allFilms.forEach(film -> {
            film.setGenres(null);
            film.setUsers(null);
        });

        List<Film> list = filmRepository.findAll().subList((page-1)*5, page*5);
        System.out.println("List: " + list.size());
        return list;
    }

    @PostMapping
    public Film store(@RequestBody Film film) {
        film.setErscheinungsjahr(new Date());
        return filmRepository.save(film);
    }

    @PutMapping("/{id}")
    public Film update(@RequestBody Film film, @PathVariable int id) {
        Film existingFilm = filmRepository.findById(id).orElse(null);
        if (existingFilm != null) {
            existingFilm.setTitle(film.getTitle());
            existingFilm.setBeschreibung(film.getBeschreibung());
            return filmRepository.save(existingFilm);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        filmRepository.deleteById(id);
    }
}

