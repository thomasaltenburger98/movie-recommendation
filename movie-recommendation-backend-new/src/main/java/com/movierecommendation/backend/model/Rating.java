package com.movierecommendation.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

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

    @Getter
    @Setter
    private Date timestamp;

    private Date aktualisiertAm;

    public Rating() {
        this.timestamp = new Date();
        this.aktualisiertAm = new Date();
    }

    public String toCSVString() {
        long timestamp = this.timestamp.getTime();
        return this.user.getId() + "," + this.film.getId() + "," + this.ratingValue + "," + timestamp;
    }

    public void setTimestampString(String timestamp) {
        this.timestamp = new Date(Long.parseLong(timestamp));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Float.compare(ratingValue, rating.ratingValue) == 0 && Objects.equals(film.getId(), rating.film.getId()) && Objects.equals(user.getId(), rating.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(film.getId(), user.getId(), ratingValue);
    }
}

