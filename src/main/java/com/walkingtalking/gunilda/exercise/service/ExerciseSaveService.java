package com.walkingtalking.gunilda.exercise.service;

import com.walkingtalking.gunilda.exercise.dto.ExerciseDTO;

public interface ExerciseSaveService {
    ExerciseDTO.SaveResponse save(ExerciseDTO.SaveCommand command);
}
