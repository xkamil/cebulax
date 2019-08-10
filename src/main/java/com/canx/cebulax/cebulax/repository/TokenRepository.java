package com.canx.cebulax.cebulax.repository;

import com.canx.cebulax.cebulax.model.ApiToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<ApiToken, UUID> {

    Optional<ApiToken> findByToken(UUID token);
}
