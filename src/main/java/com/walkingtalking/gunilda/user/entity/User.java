package com.walkingtalking.gunilda.user.entity;

import com.walkingtalking.gunilda.user.type.AgeType;
import com.walkingtalking.gunilda.user.type.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private AgeType age;

    @Column(nullable = false)
    private GenderType gender;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String sid;

    private String pwHash;

    public static User of(String sid, String pwHash) {
        return User.builder()
                .gender(GenderType.UNKNOWN)
                .age(AgeType.UNKNOWN)
                .nickname(UUID.randomUUID().toString())
                .sid(sid)
                .pwHash(pwHash)
                .build();
    }
}
