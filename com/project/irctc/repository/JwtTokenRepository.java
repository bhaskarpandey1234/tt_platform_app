package com.project.irctc.repository;

import com.project.irctc.model.token.JwtToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends MongoRepository<JwtToken, String> {
    void deleteByToken(String token);

    boolean existsByToken(String token);
}
