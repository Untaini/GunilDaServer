package com.walkingtalking.gunilda.exercise.service.impl;

import com.walkingtalking.gunilda.exercise.dto.ExerciseDTO;
import com.walkingtalking.gunilda.exercise.entity.Exercise;
import com.walkingtalking.gunilda.exercise.repository.ExerciseRepository;
import com.walkingtalking.gunilda.exercise.service.ExerciseSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseSaveServiceImpl implements ExerciseSaveService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public ExerciseDTO.SaveResponse save(ExerciseDTO.SaveCommand command) {
        Exercise exercise = command.toEntity();

        exerciseRepository.save(exercise);

        return ExerciseDTO.SaveResponse.builder()
                .build();
    }
}
