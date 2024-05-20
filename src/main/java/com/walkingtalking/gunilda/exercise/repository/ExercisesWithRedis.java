package com.walkingtalking.gunilda.exercise.repository;

import com.walkingtalking.gunilda.exercise.entity.Exercises;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercisesWithRedis extends CrudRepository<Exercises, Long> {



}
