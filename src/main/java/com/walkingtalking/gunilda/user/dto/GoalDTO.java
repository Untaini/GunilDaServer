package com.walkingtalking.gunilda.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.walkingtalking.gunilda.exercise.entity.ExerciseGoal;
import com.walkingtalking.gunilda.exercise.entity.ExerciseStatus;
import com.walkingtalking.gunilda.user.entity.UserTier;
import com.walkingtalking.gunilda.user.type.TierType;
import lombok.Builder;

import java.sql.Date;
import java.time.LocalDate;

public class GoalDTO {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Status<T>(T todayCurrent, T todayGoal) {
        public static <T> Status<T> from(T todayCurrent, T todayGoal) {
            return new Status<>(todayCurrent, todayGoal);
        }
    }

    public record Request() {
        public Command toCommand(Long userId) {
            return Command.builder()
                    .userId(userId)
                    .build();
        }
    }

    @Builder
    public record Command(Long userId) {

    }

    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Response(TierType tier, Integer exp, Status<Integer> stride, Status<Double> speed, Status<Integer> step, Boolean todayTierUp) {

        public static Response of(UserTier tier, ExerciseStatus status, ExerciseGoal goal) {
            Date today = Date.valueOf(LocalDate.now());
            Boolean todayTierUp = tier.getLatestExpUpDate().equals(today);

            Double speedFloor = Math.floor(status.getSpeed() * 10) / 10.0;

            return Response.builder()
                    .tier(tier.getTier())
                    .exp(tier.getExp())
                    .stride(Status.from(status.getStride(), goal.getStride()))
                    .speed(Status.from(speedFloor, goal.getSpeed()))
                    .step(Status.from(status.getStep(), goal.getStep()))
                    .todayTierUp(todayTierUp)
                    .build();
        }
    }
}
