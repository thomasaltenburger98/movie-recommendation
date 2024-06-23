package com.movierecommendation.backend.repository;


import com.movierecommendation.backend.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    @Modifying
    @Query("DELETE FROM Film")
    void deleteAllEntities();


    @Query("SELECT f FROM Film f WHERE LOWER(f.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Film> findByTitleContaining(@Param("title") String title);
}

