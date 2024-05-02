package com.walkingtalking.gunilda.user.entity;

import com.walkingtalking.gunilda.user.type.AgeType;
import com.walkingtalking.gunilda.user.type.GenderType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private AgeType age;

    private GenderType gender;

    @Column(unique = true)
    private String nickname;
}
