package com.walkingtalking.gunilda.user.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum SocialType {
    UNKNOWN("unknown"),
    KAKAO("kakao"),
    GOOGLE("google"),
    APPLE("apple");

    private final String social;

    private static final Map<String, SocialType> socials =
            Collections.unmodifiableMap(
                    Stream.of(SocialType.values())
                    .collect(Collectors.toMap(SocialType::getSocial, Function.identity()))
            );

    public static SocialType find(String social) {
        return Optional.ofNullable(socials.get(social)).orElse(UNKNOWN);
    }
}
