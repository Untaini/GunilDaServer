package com.walkingtalking.gunilda.exercise.dto;

import com.walkingtalking.gunilda.exercise.entity.Exercise;
import lombok.Builder;

@Builder
public record StatusDTO(Integer stride, Double speed, Integer step, Integer dataCount) {
    public static StatusDTO from(Exercise exercise) {
        return StatusDTO.builder()
                .stride(exercise.getAverageStride())
                .speed(exercise.getAverageSpeed())
                .step(exercise.getStep())
                .dataCount(exercise.getDataCount())
                .build();
    }
}
