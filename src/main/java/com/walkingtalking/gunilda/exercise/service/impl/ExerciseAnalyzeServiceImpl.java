package com.walkingtalking.gunilda.exercise.service.impl;

import com.walkingtalking.gunilda.exercise.dto.AnalyzeExerciseDTO;
import com.walkingtalking.gunilda.exercise.entity.Exercise;
import com.walkingtalking.gunilda.exercise.repository.ExerciseRepository;
import com.walkingtalking.gunilda.exercise.service.ExerciseAnalyzeService;
import com.walkingtalking.gunilda.exercise.type.DateType;
import com.walkingtalking.gunilda.user.repository.UserRepository;
import com.walkingtalking.gunilda.user.type.AgeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseAnalyzeServiceImpl implements ExerciseAnalyzeService {

    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    @Transactional(readOnly = true)
    public AnalyzeExerciseDTO.StrideResponse getStrideData(AnalyzeExerciseDTO.StrideCommand command) {
        if (command.dateType() == DateType.UNKNOWN) {
            return AnalyzeExerciseDTO.StrideResponse.builder()
                    .mine(Collections.emptyList())
                    .ageGroup(Collections.emptyList())
                    .build();
        }

        AgeType userAge = userRepository.findAgeByUserId(command.userId());
        List<Long> ageGroup = userRepository.findAllUserIdByAge(userAge);

        List<Exercise> myExercises = exerciseRepository.findAllByUserIdAndEndTimeIsBetween(command.userId(), command.startDate(), command.endDate());

        if (command.dateType() == DateType.HOUR) {
            return AnalyzeExerciseDTO.StrideResponse.builder()
                    .mine(getStrideData(collectByDateType(command.dateType(), myExercises, command.startDate(), command.endDate())))
                    .ageGroup(Collections.emptyList())
                    .build();
        }

        List<Exercise> groupExercises = exerciseRepository.findAllByUserIdIsInAndEndTimeIsBetween(ageGroup, command.startDate(), command.endDate());

        return AnalyzeExerciseDTO.StrideResponse.builder()
                .mine(getStrideData(collectByDateType(command.dateType(), myExercises, command.startDate(), command.endDate())))
                .ageGroup(getStrideData(collectByDateType(command.dateType(), groupExercises, command.startDate(), command.endDate())))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AnalyzeExerciseDTO.SpeedResponse getSpeedData(AnalyzeExerciseDTO.SpeedCommand command) {
        if (command.dateType() == DateType.UNKNOWN) {
            return AnalyzeExerciseDTO.SpeedResponse.builder()
                    .mine(Collections.emptyList())
                    .ageGroup(Collections.emptyList())
                    .build();
        }

        AgeType userAge = userRepository.findAgeByUserId(command.userId());
        List<Long> ageGroup = userRepository.findAllUserIdByAge(userAge);

        List<Exercise> myExercises = exerciseRepository.findAllByUserIdAndEndTimeIsBetween(command.userId(), command.startDate(), command.endDate());

        if (command.dateType() == DateType.HOUR) {
            return AnalyzeExerciseDTO.SpeedResponse.builder()
                    .mine(getSpeedData(collectByDateType(command.dateType(), myExercises, command.startDate(), command.endDate())))
                    .ageGroup(Collections.emptyList())
                    .build();
        }

        List<Exercise> groupExercises = exerciseRepository.findAllByUserIdIsInAndEndTimeIsBetween(ageGroup, command.startDate(), command.endDate());

        return AnalyzeExerciseDTO.SpeedResponse.builder()
                .mine(getSpeedData(collectByDateType(command.dateType(), myExercises, command.startDate(), command.endDate())))
                .ageGroup(getSpeedData(collectByDateType(command.dateType(), groupExercises, command.startDate(), command.endDate())))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AnalyzeExerciseDTO.StepResponse getStepData(AnalyzeExerciseDTO.StepCommand command) {
        if (command.dateType() == DateType.UNKNOWN) {
            return AnalyzeExerciseDTO.StepResponse.builder()
                    .mine(Collections.emptyList())
                    .ageGroup(Collections.emptyList())
                    .build();
        }

        AgeType userAge = userRepository.findAgeByUserId(command.userId());
        List<Long> ageGroup = userRepository.findAllUserIdByAge(userAge);

        List<Exercise> myExercises = exerciseRepository.findAllByUserIdAndEndTimeIsBetween(command.userId(), command.startDate(), command.endDate());

        if (command.dateType() == DateType.HOUR) {
            return AnalyzeExerciseDTO.StepResponse.builder()
                    .mine(getStepData(collectByDateType(command.dateType(), myExercises, command.startDate(), command.endDate())))
                    .ageGroup(Collections.emptyList())
                    .build();
        }

        List<Exercise> groupExercises = exerciseRepository.findAllByUserIdIsInAndEndTimeIsBetween(ageGroup, command.startDate(), command.endDate());

        return AnalyzeExerciseDTO.StepResponse.builder()
                .mine(getStepData(collectByDateType(command.dateType(), myExercises, command.startDate(), command.endDate())))
                .ageGroup(getStepData(collectByDateType(command.dateType(), groupExercises, command.startDate(), command.endDate())))
                .build();
    }

    private Map<LocalDateTime, List<Exercise>> collectByDateType(DateType dateType, List<Exercise> exercises, Timestamp startDate, Timestamp endDate) {
        Map<LocalDateTime, List<Exercise>> exerciseCollection = new HashMap<>();

        LocalDateTime start = truncate(startDate.toLocalDateTime(), dateType);
        LocalDateTime end = truncate(endDate.toLocalDateTime(), dateType).plus(1, dateType.getUnit());
        for (LocalDateTime key = start; !key.isEqual(end); key = key.plus(1, dateType.getUnit())) {
            exerciseCollection.put(key, new ArrayList<>());
        }

        exercises.forEach(exercise -> {
            LocalDateTime key = truncate(exercise.getEndTime().toLocalDateTime(), dateType);
            List<Exercise> collectedExercises = exerciseCollection.get(key);

            collectedExercises.add(exercise);
        });

        return exerciseCollection;
    }

    private LocalDateTime truncate(LocalDateTime localDateTime, DateType dateType) {
        return switch (dateType) {
            case HOUR, DAY -> localDateTime.truncatedTo(dateType.getUnit());
            case WEEK -> localDateTime.minusDays(localDateTime.getDayOfWeek().getValue()-1).truncatedTo(ChronoUnit.DAYS);
            case MONTH -> localDateTime.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);
            case UNKNOWN -> null;
        };
    }

    private List<AnalyzeExerciseDTO.Stride> getStrideData(Map<LocalDateTime, List<Exercise>> exerciseCollection) {
        return exerciseCollection.entrySet().stream()
                .map(entry -> {
                    AtomicReference<Integer> minStride = new AtomicReference<>(Integer.MAX_VALUE);
                    AtomicReference<Integer> maxStride = new AtomicReference<>(0);
                    AtomicReference<Integer> averageStride = new AtomicReference<>(0);
                    AtomicReference<Integer> dataCount = new AtomicReference<>(0);

                    entry.getValue().forEach(exercise -> {
                        minStride.set(Math.min(minStride.get(), exercise.getMinStride()));
                        maxStride.set(Math.max(maxStride.get(), exercise.getMaxStride()));
                        averageStride.updateAndGet(v -> v + exercise.getAverageStride() * exercise.getDataCount());
                        dataCount.updateAndGet(v -> v + exercise.getDataCount());
                    });

                    if (minStride.get() == Integer.MAX_VALUE) {
                        minStride.set(0);
                    }
                    dataCount.compareAndSet(0, 1);

                    return AnalyzeExerciseDTO.Stride.builder()
                            .minStride(minStride.get())
                            .maxStride(maxStride.get())
                            .averageStride(averageStride.get() / dataCount.get())
                            .date(Timestamp.valueOf(entry.getKey()))
                            .build();
                })
                .sorted(Comparator.comparing(AnalyzeExerciseDTO.Stride::date))
                .collect(Collectors.toList());
    }


    private List<AnalyzeExerciseDTO.Speed> getSpeedData(Map<LocalDateTime, List<Exercise>> exerciseCollection) {
        return exerciseCollection.entrySet().stream()
                .map(entry -> {
                    AtomicReference<Double> minSpeed = new AtomicReference<>(Double.MAX_VALUE);
                    AtomicReference<Double> maxSpeed = new AtomicReference<>(0.0);
                    AtomicReference<Double> averageSpeed = new AtomicReference<>(0.0);
                    AtomicReference<Integer> dataCount = new AtomicReference<>(0);

                    entry.getValue().forEach(exercise -> {
                        minSpeed.set(Math.min(minSpeed.get(), exercise.getMinSpeed()));
                        maxSpeed.set(Math.max(maxSpeed.get(), exercise.getMaxSpeed()));
                        averageSpeed.updateAndGet(v -> v + exercise.getAverageSpeed() * exercise.getDataCount());
                        dataCount.updateAndGet(v -> v + exercise.getDataCount());
                    });

                    if (minSpeed.get() == Double.MAX_VALUE) {
                        minSpeed.set(0.0);
                    }
                    dataCount.compareAndSet(0, 1);

                    Double minSpeedFloor = Math.floor(minSpeed.get() * 10) / 10;
                    Double maxSpeedFloor = Math.floor(maxSpeed.get() * 10) / 10;
                    Double averageSpeedFloor = Math.floor((averageSpeed.get() / dataCount.get()) * 10) / 10;

                    return AnalyzeExerciseDTO.Speed.builder()
                            .minSpeed(minSpeedFloor)
                            .maxSpeed(maxSpeedFloor)
                            .averageSpeed(averageSpeedFloor)
                            .date(Timestamp.valueOf(entry.getKey()))
                            .build();
                })
                .sorted(Comparator.comparing(AnalyzeExerciseDTO.Speed::date))
                .collect(Collectors.toList());
    }


    private List<AnalyzeExerciseDTO.Step> getStepData(Map<LocalDateTime, List<Exercise>> exerciseCollection) {
        return exerciseCollection.entrySet().stream()
                .map(entry -> {
                    AtomicReference<Integer> step = new AtomicReference<>(0);
                    Set<Long> users = new HashSet<>();

                    entry.getValue().forEach(exercise -> {
                        step.updateAndGet(v -> v + exercise.getStep());
                        users.add(exercise.getUserId());
                    });

                    return AnalyzeExerciseDTO.Step.builder()
                            .step(step.get() / users.size())
                            .date(Timestamp.valueOf(entry.getKey()))
                            .build();
                })
                .sorted(Comparator.comparing(AnalyzeExerciseDTO.Step::date))
                .collect(Collectors.toList());
    }
}
