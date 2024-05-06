package com.walkingtalking.gunilda.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.walkingtalking.gunilda.user.dto.SocialSignDTO;
import lombok.Builder;


@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SocialLoginResponseDTO(String accessToken, String refreshToken, Boolean needInitialization) {

    public static SocialLoginResponseDTO from(JwtTokenDTO.GeneratingResponse jwtResponse, SocialSignDTO.SignInResponse signInResponse) {
        return SocialLoginResponseDTO.builder()
                .accessToken(jwtResponse.accessToken())
                .refreshToken(jwtResponse.refreshToken())
                .needInitialization(signInResponse.needInitialization())
                .build();
    }

}
