package com.movierecommendation.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.movierecommendation.backend.model.views.FilmViews;
import com.movierecommendation.backend.model.views.GenreViews;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.OptionalDouble;

@Getter
@Setter
@Entity
public class Film {

    public Film() {}
    public Film(Long id) {
        this.id = id;
    }

    // Getters and Setters
    @JsonView(FilmViews.FilmView.class)
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) TODO check if this is needed
    private Long id;

    @JsonView(FilmViews.FilmView.class)
    private String title;
    //private String beschreibung;
    //private Date erscheinungsjahr;
    @JsonView(FilmViews.FilmView.class)
    private double bewertung;
    @JsonView(FilmViews.FilmView.class)
    private Long tmdbId;

    //@JsonIgnore
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonView(FilmViews.FilmViewWithGenres.class)
    private List<Genre> genres;

    @JsonView(FilmViews.FilmViewWithGenres.class)
    //@JsonView(GenreViews.GenreView.class)
    public List<Genre> getGenres() {
        return genres;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    //@JsonView(FilmViews.FilmView.class)
    private List<Rating> ratings;

    @Setter
    @Transient
    @JsonProperty("isUserLiked")
    @JsonView(FilmViews.FilmView.class)
    private boolean isUserLiked;

    @Setter
    @Transient
    @JsonProperty("isUserDisliked")
    @JsonView(FilmViews.FilmView.class)
    private boolean isUserDisliked;

    @JsonIgnore
    //@JsonView(FilmViews.FilmView.class)
    public List<User> getUsers() {
        return ratings.stream().map(Rating::getUser).toList();
    }
    public void setUsers(List<User> users) {
        this.ratings = users.stream()
                .map(user -> {
                    Rating rating = new Rating();
                    rating.setFilm(this);
                    rating.setUser(user);
                    return rating;
                }).toList();
    }

    public void calculateAndSetAverageRating() {
        OptionalDouble average = this.ratings.stream()
                .mapToDouble(Rating::getRatingValue)
                .average();

        if (average.isPresent()) {
            this.bewertung = Math.round(average.getAsDouble() * 100.0) / 100.0;
        } else {
            this.bewertung = 0;
        }
    }
}

