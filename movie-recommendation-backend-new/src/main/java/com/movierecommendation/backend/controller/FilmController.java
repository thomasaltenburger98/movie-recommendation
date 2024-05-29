package com.movierecommendation.backend.controller;

import com.movierecommendation.backend.model.Film;
import com.movierecommendation.backend.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public List<>

    @GetMapping("/hello")
    public ResponseEntity<?> returnHelloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/{id}")
    public Film show(@PathVariable int id) {
        return filmRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Film store(@RequestBody Film film) {
        film.setErstelltAm(new Date());
        return filmRepository.save(film);
    }

    @PutMapping("/{id}")
    public Film update(@RequestBody Film film, @PathVariable int id) {
        Film existingFilm = filmRepository.findById(id).orElse(null);
        if (existingFilm != null) {
            existingFilm.setTitle(film.getTitle());
            existingFilm.setBeschreibung(film.getBeschreibung());
            existingFilm.setAktualisiertAm(new Date());
            return filmRepository.save(existingFilm);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        filmRepository.deleteById(id);
    }
}

