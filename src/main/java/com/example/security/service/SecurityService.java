package com.example.security.service;

import com.example.security.model.UserLogin;
import com.example.security.repository.UserLoginRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.nio.charset.StandardCharsets;

@Service
public class SecurityService {

    @Autowired
    private UserLoginRepository userRepository;

    private static final long EXPIRATION_TIME = 900000; // 1.5 minute in milliseconds

    private static final String SECRET_KEY = "securesecuresecuresecuresecuresecuresecuresecure";

    public String generateJwtToken(String username, String password) {
        if (validateUser(username, password)) {

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                    .compact();
        }
        return null;
    }

    private boolean validateUser(String username, String password) {
        Optional<UserLogin> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            return userOptional
                    .get()
                    .getPassword()
                    .equals(password);
        }
        return false;
    }

    public Map<String, String> loginService(String username, String password) {
        String token = generateJwtToken(username, password);
        Map<String, String> response = new HashMap<>();
        if (token != null) {
            response.put("token", token);
        } else {
            response.put("error", "Invalid username or password");
        }
        return response;
    }
}