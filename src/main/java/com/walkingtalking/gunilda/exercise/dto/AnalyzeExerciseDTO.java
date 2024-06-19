package com.walkingtalking.gunilda.exercise.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.walkingtalking.gunilda.exercise.type.DateType;
import lombok.Builder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class AnalyzeExerciseDTO {

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record StrideRequest(String type, String startDate, String endDate) {

        public StrideCommand toCommand(Long userId) {
            return StrideCommand.builder()
                    .userId(userId)
                    .dateType(DateType.find(type))
                    .startDate(Timestamp.from(Instant.parse(startDate)))
                    .endDate(Timestamp.from(Instant.parse(endDate)))
                    .build();
        }
    }

    @Builder
    public record StrideCommand(Long userId, DateType dateType, Timestamp startDate, Timestamp endDate) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record StrideResponse(List<Stride> mine, List<Stride> ageGroup) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Stride(Integer minStride, Integer maxStride, Integer averageStride,
                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH-mm-ssXXX", timezone = "Asia/Seoul") Timestamp date) {
    }


    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SpeedRequest(String type, String startDate, String endDate) {

        public SpeedCommand toCommand(Long userId) {
            return SpeedCommand.builder()
                    .userId(userId)
                    .dateType(DateType.find(type))
                    .startDate(Timestamp.from(Instant.parse(startDate)))
                    .endDate(Timestamp.from(Instant.parse(endDate)))
                    .build();
        }
    }

    @Builder
    public record SpeedCommand(Long userId, DateType dateType, Timestamp startDate, Timestamp endDate) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SpeedResponse(List<Speed> mine, List<Speed> ageGroup) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Speed(Double minSpeed, Double maxSpeed, Double averageSpeed,
                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH-mm-ssXXX", timezone = "Asia/Seoul") Timestamp date) {
    }


    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record StepRequest(String type, String startDate, String endDate) {

        public StepCommand toCommand(Long userId) {
            return StepCommand.builder()
                    .userId(userId)
                    .dateType(DateType.find(type))
                    .startDate(Timestamp.from(Instant.parse(startDate)))
                    .endDate(Timestamp.from(Instant.parse(endDate)))
                    .build();
        }
    }

    @Builder
    public record StepCommand(Long userId, DateType dateType, Timestamp startDate, Timestamp endDate) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record StepResponse(List<Step> mine, List<Step> ageGroup) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Step(Integer step,
                       @JsonFormat(pattern = "yyyy-MM-dd'T'HH-mm-ssXXX", timezone = "Asia/Seoul") Timestamp date) {

    }

}
