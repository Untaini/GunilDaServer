package com.walkingtalking.gunilda.exercise.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ExerciseCollectType {
    UNKNOWN("unknown"),
    MINE("mine"),
    RECOMMEND_LONG_DISTANCE("long_distance"),
    RECOMMEND_SHORT_DISTANCE("short_distance"),
    RECOMMEND_NEARBY("nearby"),
    POPULAR("popular");

    private final String type;

    private static final Map<String, ExerciseCollectType> exerciseCollectTypes =
            Collections.unmodifiableMap(
                    Stream.of(ExerciseCollectType.values())
                            .collect(Collectors.toMap(ExerciseCollectType::getType, Function.identity()))
            );

    public static ExerciseCollectType find(String age) {
        return Optional.ofNullable(exerciseCollectTypes.get(age)).orElse(UNKNOWN);
    }
}
