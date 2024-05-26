package com.walkingtalking.gunilda.exercise.service;

import com.walkingtalking.gunilda.exercise.dto.StatusUpdateDTO;

public interface ExerciseStatusUpdateService {

    StatusUpdateDTO.Response updateStatus(StatusUpdateDTO.Command command);

}
