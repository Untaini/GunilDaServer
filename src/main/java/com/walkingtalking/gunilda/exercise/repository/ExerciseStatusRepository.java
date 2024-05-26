package com.walkingtalking.gunilda.exercise.repository;

import com.walkingtalking.gunilda.exercise.entity.ExerciseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.Optional;

public interface ExerciseStatusRepository extends JpaRepository<ExerciseStatus, Long> {

    Optional<ExerciseStatus> findByUserIdAndDate(Long userId, Date date);

}
