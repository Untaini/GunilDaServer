package com.walkingtalking.gunilda.user.type;

import com.fasterxml.jackson.annotation.JsonValue;
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
public enum AgeType {
    UNKNOWN("unknown"),
    UNDER_FIFTY("~50"),
    FIFTY_TO_FIFTY_FIVE("50~55"),
    FIFTY_FIVE_TO_SIXTY("55~60"),
    SIXTY_TO_SEVENTY("60~70"),
    SEVENTY_TO_EIGHTY("70~80"),
    OVER_EIGHTY("80~");

    @JsonValue
    private final String age;

    private static final Map<String, AgeType> ages =
            Collections.unmodifiableMap(
                    Stream.of(AgeType.values())
                    .collect(Collectors.toMap(AgeType::getAge, Function.identity()))
            );

    public static AgeType find(String age) {
        return Optional.ofNullable(ages.get(age)).orElse(UNKNOWN);
    }

}
