package com.walkingtalking.gunilda.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.walkingtalking.gunilda.exercise.dto.SearchExerciseDTO;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

public class SearchResultDTO {


    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record CombineMyCoursesResponse(List<CombineMyCourse> results, Boolean hasNext, Long nextCourseId) {
        public static CombineMyCoursesResponse of(SearchExerciseDTO.SearchRecentExerciseResponse exerciseResponse,
                                                  List<CourseSearchDTO.MyCourse> courses) {

            //성능 개선..?
            List<CombineMyCourse> results = exerciseResponse.results().stream()
                    .map(exercise -> {
                        CourseSearchDTO.MyCourse course = courses.stream()
                                .filter(elem -> elem.courseId() == exercise.courseId())
                                .findFirst()
                                .get();

                        return CombineMyCourse.of(exercise, course);
                    })
                    .toList();

            return CombineMyCoursesResponse.builder()
                    .results(results)
                    .hasNext(exerciseResponse.hasNext())
                    .nextCourseId(exerciseResponse.nextCourseId())
                    .build();
        }
    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record CombineMyCourse(Long courseId, Boolean doShare, String courseName, Integer distance, Long time, List<CoursePointDTO> course) {
        public static CombineMyCourse of(SearchExerciseDTO.ExerciseForCourse exercise, CourseSearchDTO.MyCourse course) {
            return CombineMyCourse.builder()
                    .courseId(course.courseId())
                    .doShare(course.doShare())
                    .courseName(Optional.ofNullable(course.courseName()).orElse(""))
                    .distance(exercise.distance())
                    .time(exercise.time())
                    .course(course.coursePoints())
                    .build();
        }
    }
}
