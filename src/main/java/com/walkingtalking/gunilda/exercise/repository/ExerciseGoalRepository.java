package com.walkingtalking.gunilda.exercise.repository;

import com.walkingtalking.gunilda.exercise.entity.ExerciseGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseGoalRepository extends JpaRepository<ExerciseGoal, ExerciseGoal.PK> {

}
