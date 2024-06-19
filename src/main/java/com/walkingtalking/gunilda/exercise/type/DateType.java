package com.walkingtalking.gunilda.exercise.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DateType {
    UNKNOWN("unknown", null),
    HOUR("hour", ChronoUnit.HOURS),
    DAY("day", ChronoUnit.DAYS),
    WEEK("week", ChronoUnit.WEEKS),
    MONTH("month", ChronoUnit.MONTHS);

    @JsonValue
    private final String date;
    private final TemporalUnit unit;

    private static final Map<String, DateType> dates =
            Collections.unmodifiableMap(
                    Stream.of(DateType.values())
                            .collect(Collectors.toMap(DateType::getDate, Function.identity()))
            );

    public static DateType find(String date) {
        return Optional.ofNullable(dates.get(date)).orElse(UNKNOWN);
    }
}
