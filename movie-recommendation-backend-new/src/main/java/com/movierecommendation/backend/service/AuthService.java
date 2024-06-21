package com.movierecommendation.backend.service;

import com.movierecommendation.backend.model.Token;
import com.movierecommendation.backend.model.User;
import com.movierecommendation.backend.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generate a key

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        // Handle exception when user is not found
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Long getUserIdByUsername(String username) {
        return userRepository.findIdByUsername(username);
    }

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

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

    public boolean logoutWithToken(String token) {
        Token tokenObj = tokenRepository.findByTokenValue(token);
        if (tokenObj != null) {
            tokenRepository.delete(tokenObj);
            return true;
        }
        return false;
    }

    public SecretKey getSecretKey() {
        return key;
    }

    public Token generateToken(User user) {
        //String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded()); // Encode the key to a string

        String tokenValue = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 days
                .signWith(this.key) // Use the generated key
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

    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}

