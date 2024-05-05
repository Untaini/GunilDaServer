package com.walkingtalking.gunilda.user.entity;

import com.walkingtalking.gunilda.user.type.SocialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;

    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private Long userId;

    public static Social of(SocialType socialType, String socialId, Long userId) {
        return Social.builder()
                .socialType(socialType)
                .socialId(socialId)
                .userId(userId)
                .build();
    }
}
