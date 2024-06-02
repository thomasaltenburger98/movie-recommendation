package com.movierecommendation.backend.repository;



import com.movierecommendation.backend.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepositoryCustom {
    void deleteByUserId(Long userId);
    Token findByTokenValue(String tokenValue);
    long countByUserId(Long userId);
}

