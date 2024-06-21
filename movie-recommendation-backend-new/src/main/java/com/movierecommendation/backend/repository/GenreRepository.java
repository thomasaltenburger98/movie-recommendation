package com.movierecommendation.backend.repository;

import com.movierecommendation.backend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByName(String name);

    @Modifying
    @Query("DELETE FROM Genre")
    void deleteAllEntities();
}
