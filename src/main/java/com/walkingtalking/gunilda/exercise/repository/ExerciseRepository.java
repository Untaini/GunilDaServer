package com.walkingtalking.gunilda.exercise.repository;

import com.walkingtalking.gunilda.exercise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findByCourseId(Long courseId);
    List<Exercise> findAllByUserId(Long userId);

    List<Exercise> findAllByIdIsIn(List<Long> ids);

    List<Exercise> findAllByUserIdAndEndTimeIsBetween(Long userId, Timestamp startDate, Timestamp endDate);
    List<Exercise> findAllByUserIdIsInAndEndTimeIsBetween(List<Long> userIds, Timestamp startDate, Timestamp endDate);

}
