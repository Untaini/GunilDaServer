package com.walkingtalking.gunilda.user.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GenderType {
    UNKNOWN("unknown"),
    MAN("man"),
    WOMAN("woman");

    @JsonValue
    private final String gender;

    private static final Map<String, GenderType> genders =
            Collections.unmodifiableMap(
                    Stream.of(GenderType.values())
                    .collect(Collectors.toMap(GenderType::getGender, Function.identity()))
            );

    public static GenderType find(String type) {
        return Optional.ofNullable(genders.get(type)).orElse(UNKNOWN);
    }
}
