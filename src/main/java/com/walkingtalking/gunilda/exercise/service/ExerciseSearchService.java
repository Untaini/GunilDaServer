package com.walkingtalking.gunilda.exercise.service;

import com.walkingtalking.gunilda.exercise.dto.SearchExerciseDTO;

public interface ExerciseSearchService {
    SearchExerciseDTO.SearchRecentExerciseResponse searchRecentExercises(SearchExerciseDTO.SearchRecentExerciseCommand command);
}
