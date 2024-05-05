package com.walkingtalking.gunilda.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.util.Date;

public class JwtTokenDTO {

    @Builder
    public record GeneratingWithIdRequest(Long userId) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record GeneratingWithRefreshTokenRequest(String refreshToken) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record GeneratingResponse(String accessToken, String refreshToken) {

    }

    @Builder
    public record TokenPayload(Long userId, Date issuedAt, Date expiration) {

    }
}
