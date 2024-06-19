package com.walkingtalking.gunilda.exercise.service;

import com.walkingtalking.gunilda.exercise.dto.AnalyzeExerciseDTO;

public interface ExerciseAnalyzeService {

    AnalyzeExerciseDTO.StrideResponse getStrideData(AnalyzeExerciseDTO.StrideCommand command);
    AnalyzeExerciseDTO.SpeedResponse getSpeedData(AnalyzeExerciseDTO.SpeedCommand command);
    AnalyzeExerciseDTO.StepResponse getStepData(AnalyzeExerciseDTO.StepCommand command);

}
