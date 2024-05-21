package com.walkingtalking.gunilda.exercise.service.impl;

import com.walkingtalking.gunilda.exercise.dto.ExerciseDTO;
import com.walkingtalking.gunilda.exercise.entity.Exercise;
import com.walkingtalking.gunilda.exercise.exception.ExerciseException;
import com.walkingtalking.gunilda.exercise.exception.type.ExerciseExceptionType;
import com.walkingtalking.gunilda.exercise.repository.ExerciseRepository;
import com.walkingtalking.gunilda.exercise.repository.ExercisesWithRedis;
import com.walkingtalking.gunilda.exercise.service.ExerciseSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseSaveServiceImpl implements ExerciseSaveService {

    private final ExerciseRepository exerciseRepository;
    private final ExercisesWithRedis exercisesCacheRepository;

    @Override
    public ExerciseDTO.SaveResponse save(ExerciseDTO.SaveCommand command) {
        Exercise exercise = command.toEntity();

        try {
            exerciseRepository.save(exercise);
        } catch(Exception e) {
            throw new ExerciseException(ExerciseExceptionType.EXERCISE_CANNOT_SAVE);
        }

        exercisesCacheRepository.deleteById(command.userId());

        return ExerciseDTO.SaveResponse.builder()
                .build();
    }
}
