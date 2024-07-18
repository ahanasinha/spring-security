package com.example.security.repository;

import com.example.security.model.UserLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserLoginRepository extends MongoRepository<UserLogin, String> {
    Optional<UserLogin> findByUsername(String username);
}