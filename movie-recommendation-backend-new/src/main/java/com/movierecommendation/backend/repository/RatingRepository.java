package com.movierecommendation.backend.repository;

import com.movierecommendation.backend.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    java.util.List<Rating> findAllByUserId(int userId);

}
