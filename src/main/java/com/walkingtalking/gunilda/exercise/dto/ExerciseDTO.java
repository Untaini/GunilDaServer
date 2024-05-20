package com.walkingtalking.gunilda.exercise.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.walkingtalking.gunilda.course.dto.CourseSaveDTO;
import com.walkingtalking.gunilda.course.dto.CoursePointDTO;
import com.walkingtalking.gunilda.exercise.entity.Exercise;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;

public class ExerciseDTO {

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SaveRequest(Integer minStride,
                              Integer maxStride,
                              Integer averageStride,
                              Double minSpeed,
                              Double maxSpeed,
                              Double averageSpeed,
                              Integer step,
                              Double stability,
                              Integer distance,
                              Timestamp startTime,
                              Timestamp endTime,
                              List<CoursePointDTO> course,
                              Boolean doShareCourse,
                              String courseName) {

        public CourseSaveDTO.Command toCourseCommand(Long userId) {
            return CourseSaveDTO.Command.builder()
                    .userId(userId)
                    .course(course)
                    .doShareCourse(doShareCourse)
                    .courseName(courseName)
                    .build();
        }

        public SaveCommand toExerciseCommand(Long userId, Long courseId) {
            return SaveCommand.builder()
                    .userId(userId)
                    .courseId(courseId)
                    .minStride(minStride)
                    .maxStride(maxStride)
                    .averageStride(averageStride)
                    .minSpeed(minSpeed)
                    .maxSpeed(maxSpeed)
                    .averageSpeed(averageSpeed)
                    .step(step)
                    .stability(stability)
                    .distance(distance)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
        }
     }

    @Builder
    public record SaveCommand(Long userId,
                              Long courseId,
                              Integer minStride,
                              Integer maxStride,
                              Integer averageStride,
                              Double minSpeed,
                              Double maxSpeed,
                              Double averageSpeed,
                              Integer step,
                              Double stability,
                              Integer distance,
                              Timestamp startTime,
                              Timestamp endTime) {

        public Exercise toEntity() {
            return Exercise.builder()
                    .userId(userId)
                    .courseId(courseId)
                    .minStride(minStride)
                    .maxStride(maxStride)
                    .averageStride(averageStride)
                    .minSpeed(minSpeed)
                    .maxSpeed(maxSpeed)
                    .averageSpeed(averageSpeed)
                    .step(step)
                    .stability(stability)
                    .distance(distance)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
        }
    }

    @Builder
    public record SaveResponse() {

    }

}
