package com.walkingtalking.gunilda.exercise.dto;

import lombok.Builder;

public class StatusUpdateDTO {

    @Builder
    public record Command(Long userId, StatusDTO status) {

    }

    @Builder
    public record Response() {

    }
}
