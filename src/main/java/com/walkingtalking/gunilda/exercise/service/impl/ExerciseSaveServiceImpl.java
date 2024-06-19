package com.walkingtalking.gunilda.exercise.service.impl;

import com.walkingtalking.gunilda.exercise.dto.ExerciseDTO;
import com.walkingtalking.gunilda.exercise.dto.StatusDTO;
import com.walkingtalking.gunilda.exercise.dto.StatusUpdateDTO;
import com.walkingtalking.gunilda.exercise.entity.Exercise;
import com.walkingtalking.gunilda.exercise.exception.ExerciseException;
import com.walkingtalking.gunilda.exercise.exception.type.ExerciseExceptionType;
import com.walkingtalking.gunilda.exercise.repository.ExerciseRepository;
import com.walkingtalking.gunilda.exercise.repository.ExercisesWithRedis;
import com.walkingtalking.gunilda.exercise.service.ExerciseSaveService;
import com.walkingtalking.gunilda.exercise.service.ExerciseStatusUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExerciseSaveServiceImpl implements ExerciseSaveService {

    private final ExerciseRepository exerciseRepository;
    private final ExercisesWithRedis exercisesCacheRepository;
    private final ExerciseStatusUpdateService statusUpdateService;

    @Override
    @Transactional
    public ExerciseDTO.SaveResponse save(ExerciseDTO.SaveCommand command) {
        Exercise exercise = command.toEntity();

        //save에 성공하든 실패하든 항상 삭제하도록 변경
        exercisesCacheRepository.deleteById(command.userId());

        try {
            exerciseRepository.save(exercise);
        } catch(Exception e) {
            throw new ExerciseException(ExerciseExceptionType.EXERCISE_CANNOT_SAVE);
        }

        StatusUpdateDTO.Command updateCommand = StatusUpdateDTO.Command.builder()
                .userId(command.userId())
                .status(StatusDTO.from(exercise))
                .build();

        statusUpdateService.updateStatus(updateCommand);

        return ExerciseDTO.SaveResponse.builder()
                .build();
    }
}
