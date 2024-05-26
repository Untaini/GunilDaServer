package com.walkingtalking.gunilda.exercise.entity;

import com.walkingtalking.gunilda.user.type.TierType;
import com.walkingtalking.gunilda.user.type.AgeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Entity
@Getter
public class ExerciseGoal {

    @Builder
    public record PK(AgeType age, TierType tier) implements Serializable {

    }

    @EmbeddedId
    private PK pk;

    @Column(nullable = false)
    private Integer stride;

    @Column(nullable = false)
    private Double speed;

    @Column(nullable = false)
    private Integer step;

}
