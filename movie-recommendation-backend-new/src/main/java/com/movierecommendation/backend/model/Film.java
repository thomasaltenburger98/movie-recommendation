package com.movierecommendation.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Genre> genres;

    @JsonIgnore
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @Setter
    @Transient
    @JsonProperty("isUserLiked")
    private boolean isUserLiked;

    @JsonIgnore
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
}

