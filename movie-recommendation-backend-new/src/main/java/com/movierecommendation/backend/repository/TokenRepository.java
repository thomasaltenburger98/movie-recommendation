package com.movierecommendation.backend.repository;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends TokenRepositoryBasic, TokenRepositoryCustom {

    @Modifying
    @Query("DELETE FROM Token")
    void deleteAllEntities();
}

