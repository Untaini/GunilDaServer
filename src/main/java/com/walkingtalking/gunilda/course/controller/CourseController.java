package com.walkingtalking.gunilda.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walkingtalking.gunilda.course.dto.CourseSearchDTO;
import com.walkingtalking.gunilda.course.dto.SearchResultDTO;
import com.walkingtalking.gunilda.course.service.CourseSearchService;
import com.walkingtalking.gunilda.exercise.dto.SearchExerciseDTO;
import com.walkingtalking.gunilda.exercise.service.ExerciseSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {

    private final ExerciseSearchService exerciseSearchService;
    private final CourseSearchService courseSearchService;

    @GetMapping("mine")
    public ResponseEntity<SearchResultDTO.CombineMyCoursesResponse> getMyCourses(@RequestParam Map<String, String> params,
                                                                                 @RequestAttribute Long userId) {

        ObjectMapper mapper = new ObjectMapper();
        SearchExerciseDTO.SearchRecentExerciseRequest exerciseRequest = mapper.convertValue(params, SearchExerciseDTO.SearchRecentExerciseRequest.class);

        SearchExerciseDTO.SearchRecentExerciseResponse exerciseResponse = exerciseSearchService.searchRecentExercises(exerciseRequest.toCommand(userId));

        List<Long> courseIds = exerciseResponse.results().stream()
                .map(SearchExerciseDTO.ExerciseForCourse::courseId)
                .toList();

        CourseSearchDTO.SearchCommand command = CourseSearchDTO.SearchCommand.builder()
                .courseIds(courseIds)
                .build();

        CourseSearchDTO.MyCoursesResponse coursesResponse = courseSearchService.searchMyCourses(command);

        return ResponseEntity.ok(SearchResultDTO.CombineMyCoursesResponse.of(exerciseResponse, coursesResponse.courses()));
    }

}
