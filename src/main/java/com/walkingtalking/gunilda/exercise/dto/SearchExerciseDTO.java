package com.walkingtalking.gunilda.exercise.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.walkingtalking.gunilda.exercise.entity.Exercise;
import lombok.Builder;

import java.util.List;

public class SearchExerciseDTO {

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SearchRecentExerciseRequest(Integer showCount, Long nextCourseId) {

        public SearchRecentExerciseCommand toCommand(Long userId) {
            return SearchRecentExerciseCommand.builder()
                    .userId(userId)
                    .showCount(showCount)
                    .nextCourseId(nextCourseId)
                    .build();
        }
    }

    @Builder
    public record SearchRecentExerciseCommand(Long userId, Integer showCount, Long nextCourseId) {

    }

    @Builder
    public record SearchRecentExerciseResponse(List<ExerciseForCourse> results, Boolean hasNext, Long nextCourseId) {

    }

    @Builder
    public record ExerciseForCourse(Long courseId, Long time, Integer distance, Integer nearby) {
        public static ExerciseForCourse from(Exercise exercise) {
            return ExerciseForCourse.builder()
                    .courseId(exercise.getCourseId())
                    .time((exercise.getEndTime().getTime() - exercise.getStartTime().getTime()) / 1000) //second로 변환
                    .distance(exercise.getDistance())
                    .build();
        }
    }
}
