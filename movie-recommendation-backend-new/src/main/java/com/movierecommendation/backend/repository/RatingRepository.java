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

    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId AND r.film.id = :filmId")
    Rating getRatingByUserIdAndFilmId(long userId, long filmId);

    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId AND r.film.id = :filmId AND r.ratingValue = :ratingValue")
    Rating getRatingByUserIdAndFilmIdAndRatingValue(long userId, long filmId, float ratingValue);

}
