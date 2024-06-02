package com.movierecommendation.backend.repository;

import com.movierecommendation.backend.model.Token;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

public class TokenRepositoryCustomImpl implements TokenRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void deleteByUserId(Long userId) {

    }

    @Override
    public Token findByTokenValue(String tokenValue) {
        TypedQuery<Token> query = em.createQuery("SELECT t FROM Token t WHERE t.tokenValue = :tokenValue", Token.class);
        query.setParameter("tokenValue", tokenValue);
        return query.getSingleResult();
    }

    @Override
    public long countByUserId(Long userId) {
        return 0;
    }
}
