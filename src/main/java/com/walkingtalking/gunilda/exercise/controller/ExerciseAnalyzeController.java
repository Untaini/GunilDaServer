package com.walkingtalking.gunilda.exercise.controller;

import com.walkingtalking.gunilda.exercise.dto.AnalyzeExerciseDTO;
import com.walkingtalking.gunilda.exercise.service.ExerciseAnalyzeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercise/stats")
@Slf4j
public class ExerciseAnalyzeController {

    private final ExerciseAnalyzeService analyzeService;

    @GetMapping("/stride")
    public ResponseEntity<AnalyzeExerciseDTO.StrideResponse> getStrideData(@RequestAttribute Long userId,
                                                                           @ModelAttribute AnalyzeExerciseDTO.StrideRequest request) {

        return ResponseEntity.ok(analyzeService.getStrideData(request.toCommand(userId)));
    }

    @GetMapping("/speed")
    public ResponseEntity<AnalyzeExerciseDTO.SpeedResponse> getSpeedData(@RequestAttribute Long userId,
                                                                         @ModelAttribute AnalyzeExerciseDTO.SpeedRequest request) {

        return ResponseEntity.ok(analyzeService.getSpeedData(request.toCommand(userId)));
    }

    @GetMapping("/step")
    public ResponseEntity<AnalyzeExerciseDTO.StepResponse> getStepData(@RequestAttribute Long userId,
                                                                       @ModelAttribute AnalyzeExerciseDTO.StepRequest request) {

        return ResponseEntity.ok(analyzeService.getStepData(request.toCommand(userId)));
    }

}
