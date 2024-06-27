package com.movierecommendation.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.movierecommendation.backend.model.views.GenreViews;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(GenreViews.GenreView.class)
    private Long id;

    @Column(unique = true)
    @JsonView(GenreViews.GenreView.class)
    private String name;

    @JsonIgnore
    //@JsonView(GenreViews.GenreViewWithFilms.class)
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "genres")
    private List<Film> films;

    // Getters and Setters
    @JsonView(GenreViews.GenreView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(GenreViews.GenreView.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    //@JsonView(GenreViews.GenreViewWithFilms.class)
    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

}

