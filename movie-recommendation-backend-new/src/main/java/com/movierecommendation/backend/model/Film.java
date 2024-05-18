package com.movierecommendation.backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String beschreibung;
    private Date erstelltAm;
    private Date aktualisiertAm;

    @ManyToMany
    private List<Genre> genres;

    @ManyToMany
    private List<User> users;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Date getErstelltAm() {
        return erstelltAm;
    }
    public void setErstelltAm(Date erstelltAm) {
        this.erstelltAm = erstelltAm;
    }

    public Date getAktualisiertAm() {
        return aktualisiertAm;
    }
    public void setAktualisiertAm(Date aktualisiertAm) {
        this.aktualisiertAm = aktualisiertAm;
    }

    public List<Genre> getGenres() {
        return genres;
    }
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
}

