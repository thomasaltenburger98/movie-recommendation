package com.movierecommendation.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    private float ratingValue;

    @Setter
    private Date erstelltAm;
    private Date aktualisiertAm;

    public Rating() {
        this.erstelltAm = new Date();
        this.aktualisiertAm = new Date();
    }

    public String toCSVString() {
        long timestamp = this.erstelltAm.getTime();
        return this.user.getId() + "," + this.film.getId() + "," + this.ratingValue + "," + timestamp;
    }

}

