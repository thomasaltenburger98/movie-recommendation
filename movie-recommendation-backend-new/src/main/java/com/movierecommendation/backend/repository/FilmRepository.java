package com.movierecommendation.backend.repository;


import com.movierecommendation.backend.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    @Modifying
    @Query("DELETE FROM Film")
    void deleteAllEntities();
}

