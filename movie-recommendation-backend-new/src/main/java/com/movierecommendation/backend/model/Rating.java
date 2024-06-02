package com.movierecommendation.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private float ratingValue;
    private Date erstelltAm;
    private Date aktualisiertAm;

    // Getters and Setters
    public void setFilm(Film film) {
        this.film = film;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public void setErstelltAm(Date erstelltAm) {
        this.erstelltAm = erstelltAm;
    }

    public float getRatingValue() {
        return ratingValue;
    }
}

