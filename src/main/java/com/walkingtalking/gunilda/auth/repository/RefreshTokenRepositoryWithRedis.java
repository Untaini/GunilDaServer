package com.walkingtalking.gunilda.auth.repository;

import com.walkingtalking.gunilda.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepositoryWithRedis extends CrudRepository<RefreshToken, String> {

    boolean existsByUserId(Long userId);
    Optional<RefreshToken> findByUserId(Long userId);

}
