package com.movierecommendation.backend.repository;

import com.movierecommendation.backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    java.util.List<Rating> findAllByUserId(int userId);
    java.util.List<Rating> findAllByUserUsername(String username);

    @Modifying
    @Query("DELETE FROM Rating")
    void deleteAllEntities();

}
