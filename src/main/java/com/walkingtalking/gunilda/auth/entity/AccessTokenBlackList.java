package com.walkingtalking.gunilda.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Data
@RedisHash
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessTokenBlackList {

    @Id
    private String accessToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long ttl;

}
