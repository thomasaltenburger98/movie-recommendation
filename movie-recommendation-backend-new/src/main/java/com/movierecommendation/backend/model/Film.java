package com.movierecommendation.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Film {
    // Getters and Setters
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) TODO check if this is needed
    private Long id;

    private String title;
    //private String beschreibung;
    //private Date erscheinungsjahr;
    private int bewertung;
    private Long tmdbId;

    @ManyToMany
    private List<Genre> genres;

    @ManyToMany
    private List<User> users;
}

