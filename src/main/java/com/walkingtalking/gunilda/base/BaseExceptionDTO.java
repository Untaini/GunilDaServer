package com.walkingtalking.gunilda.base;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BaseExceptionDTO(String errorCode, String message) {
    public static BaseExceptionDTO from(BaseException exception) {
        return BaseExceptionDTO.builder()
                .errorCode(exception.getExceptionType().getErrorCode())
                .message(exception.getExceptionType().getMessage())
                .build();
    }

}
