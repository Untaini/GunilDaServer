package com.walkingtalking.gunilda.user.type;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TierType {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT;

    @JsonValue
    public Integer getValue() {
        return this.ordinal();
    }

    public Boolean isHighestTier() {
        return this.equals(EXPERT);
    }

    //EXPERT는 최고등급이어서 EXPERT를 그대로 반환함
    public static TierType getNextTier(TierType tier) {
        TierType nextTier = switch (tier) {
            case BEGINNER -> INTERMEDIATE;
            case INTERMEDIATE -> ADVANCED;
            case ADVANCED, EXPERT -> EXPERT;
        };

        return nextTier;
    }
}
