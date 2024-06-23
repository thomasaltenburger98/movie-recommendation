package com.movierecommendation.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

    public User() {
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    //    public void setId(Long id) {
//        this.id = id;
//    }

    public List<Film> getFilms() {
        return ratings.stream().map(Rating::getFilm).toList();
    }

    public void setFilms(List<Film> films) {
        this.ratings = films.stream()
                .map(film -> {
                    Rating rating = new Rating();
                    rating.setFilm(film);
                    rating.setUser(this);
                    return rating;
                }).toList();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

}

