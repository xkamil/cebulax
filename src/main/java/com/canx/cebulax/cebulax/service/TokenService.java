package com.canx.cebulax.cebulax.service;

import com.canx.cebulax.cebulax.model.ApiToken;
import com.canx.cebulax.cebulax.model.User;
import com.canx.cebulax.cebulax.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${authentication.token-lifetime}")
    private Long tokenLifetime;

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public ApiToken create(User user) {
        ApiToken apiToken = new ApiToken(user);
        apiToken.setValidUntil(createValidUntil());
        return tokenRepository.save(apiToken);
    }

    public void refresh(UUID token) {
        tokenRepository.findByToken(token).ifPresent(apiToken -> {
            apiToken.setValidUntil(createValidUntil());
            tokenRepository.save(apiToken);
        });
    }

    public Optional<ApiToken> getByToken(UUID token) {
        return tokenRepository.findByToken(token);
    }

    private LocalDateTime createValidUntil() {
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.plus(tokenLifetime, ChronoUnit.SECONDS);
    }

}
