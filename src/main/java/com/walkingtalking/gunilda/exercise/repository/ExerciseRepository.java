package com.walkingtalking.gunilda.exercise.repository;

import com.walkingtalking.gunilda.exercise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
