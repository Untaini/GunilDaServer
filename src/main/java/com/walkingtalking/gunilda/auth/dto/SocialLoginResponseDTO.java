package com.walkingtalking.gunilda.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;


@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SocialLoginResponseDTO(String accessToken, String refreshToken, Boolean needInitialization) {


}
