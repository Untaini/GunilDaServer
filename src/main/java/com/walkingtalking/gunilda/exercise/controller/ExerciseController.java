package com.walkingtalking.gunilda.exercise.controller;

import com.walkingtalking.gunilda.course.dto.CourseSaveDTO;
import com.walkingtalking.gunilda.course.service.CourseSaveService;
import com.walkingtalking.gunilda.exercise.dto.ExerciseDTO;
import com.walkingtalking.gunilda.exercise.service.ExerciseSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercise")
@Slf4j
public class ExerciseController {

    private final ExerciseSaveService exerciseSaveService;
    private final CourseSaveService courseSaveService;

    @PostMapping()
    public ResponseEntity<ExerciseDTO.SaveResponse> save(@RequestBody ExerciseDTO.SaveRequest request,
                                                         @RequestAttribute Long userId) {

        CourseSaveDTO.Response courseResponse = courseSaveService.save(request.toCourseCommand(userId));

        return ResponseEntity.ok(exerciseSaveService.save(request.toExerciseCommand(userId, courseResponse.courseId())));
    }


}
