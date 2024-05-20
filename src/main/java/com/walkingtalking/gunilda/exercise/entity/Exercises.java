package com.walkingtalking.gunilda.exercise.entity;

import com.walkingtalking.gunilda.exercise.type.ExerciseCollectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.List;

@RedisHash
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercises {

    @Id
    private Long userId;

    private ExerciseCollectType collectType;

    private List<Long> exerciseIds;

    @TimeToLive
    private Long ttl;
}
