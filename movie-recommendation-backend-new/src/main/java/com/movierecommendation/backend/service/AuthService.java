package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Token;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.repository.TokenRepository;
import com.movierecommendation.backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User register(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            User user = new User(username, password);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public boolean logout(Long userId) {
        tokenRepository.deleteByUserId(userId);
        long tokenCount = tokenRepository.countByUserId(userId);
        return tokenCount == 0;
    }

    public Token generateToken(User user) {
        String tokenValue = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 days
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())
                .compact();
        Token token = new Token();
        token.setTokenValue(tokenValue);
        token.setExpiration(new Date(System.currentTimeMillis() + 864_000_000));
        tokenRepository.save(token);
        return token;
    }

    public boolean verifyToken(String tokenValue) {
        Token token = tokenRepository.findByTokenValue(tokenValue);
        return token != null && token.getExpiration().after(new Date());
    }
}

