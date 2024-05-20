package com.walkingtalking.gunilda.exercise.service.impl;

import com.walkingtalking.gunilda.exercise.dto.SearchExerciseDTO;
import com.walkingtalking.gunilda.exercise.entity.Exercise;
import com.walkingtalking.gunilda.exercise.entity.Exercises;
import com.walkingtalking.gunilda.exercise.exception.ExerciseException;
import com.walkingtalking.gunilda.exercise.exception.type.ExerciseExceptionType;
import com.walkingtalking.gunilda.exercise.repository.ExerciseRepository;
import com.walkingtalking.gunilda.exercise.repository.ExercisesWithRedis;
import com.walkingtalking.gunilda.exercise.service.ExerciseSearchService;
import com.walkingtalking.gunilda.exercise.type.ExerciseCollectType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseSearchServiceImpl implements ExerciseSearchService {

    private final ExerciseRepository exerciseRepository;
    private final ExercisesWithRedis exercisesCacheRepository;

    private static final Long EXERCISES_CACHE_TIME = 600L;

    @Override
    @Transactional(readOnly = true)
    public SearchExerciseDTO.SearchRecentExerciseResponse searchRecentExercises(SearchExerciseDTO.SearchRecentExerciseCommand command) {
        final Long searchId;

        if (command.nextCourseId() != null) {
            Exercise exercise = exerciseRepository.findByCourseId(command.nextCourseId())
                    .orElseThrow(() -> new ExerciseException(ExerciseExceptionType.EXERCISE_NOT_FOUND));

            if (!exercise.getUserId().equals(command.userId())) {
                throw new ExerciseException(ExerciseExceptionType.EXERCISE_NOT_ALLOWED);
            }

            searchId = exercise.getId();
        } else {
            searchId = 0L;
        }

        if (command.showCount() == null) {
            throw new ExerciseException(ExerciseExceptionType.INSUFFICIENT_PARAMETER);
        }

        List<Long> exerciseIds = getMyExerciseIds(command.userId());

        //운동을 한 번도 저장하지 않은 경우
        if (exerciseIds.size() == 0) {
            return SearchExerciseDTO.SearchRecentExerciseResponse.builder()
                    .results(Collections.emptyList())
                    .hasNext(false)
                    .build();
        }

        //추후 성능 개선 필요 가능성 있음
        List<Long> selectedExerciseIds = exerciseIds.stream()
                .filter(elem -> elem >= searchId)
                .limit(command.showCount() + 1)
                .toList();

        List<SearchExerciseDTO.ExerciseForCourse> selectedExercises = exerciseRepository.findAllByIdIsIn(selectedExerciseIds)
                .stream()
                .map(SearchExerciseDTO.ExerciseForCourse::from)
                .toList();

        //가장 마지막에 있는 운동 정보를 가져옴
        Boolean hasNext = selectedExerciseIds.size() > command.showCount();
        SearchExerciseDTO.ExerciseForCourse nextCourse = selectedExercises.get(selectedExercises.size() - 1);

        Long nextCourseId = hasNext ? nextCourse.courseId() : null;
        List<SearchExerciseDTO.ExerciseForCourse> results = hasNext
                ? selectedExercises.subList(0, command.showCount())
                : selectedExercises;

        return SearchExerciseDTO.SearchRecentExerciseResponse.builder()
                .results(results)
                .hasNext(hasNext) //같지 않다면 if 조건이 작동하지 않은 것
                .nextCourseId(nextCourseId)
                .build();
    }

    @Transactional
    public List<Long> getMyExerciseIds(Long userId) {
        if (exercisesCacheRepository.existsById(userId)) {
            Exercises cache = exercisesCacheRepository.findById(userId).get();

            if (cache.getCollectType().equals(ExerciseCollectType.MINE)) {
                return cache.getExerciseIds();
            }
        }

        List<Long> exerciseIds = exerciseRepository.findAllByUserId(userId).stream()
                .map(Exercise::getId)
                .toList();

        Exercises exercisesIntoRedis = Exercises.builder()
                .userId(userId)
                .collectType(ExerciseCollectType.MINE)
                .exerciseIds(exerciseIds)
                .ttl(EXERCISES_CACHE_TIME)
                .build();

        exercisesCacheRepository.save(exercisesIntoRedis);

        return exerciseIds;
    }
}
