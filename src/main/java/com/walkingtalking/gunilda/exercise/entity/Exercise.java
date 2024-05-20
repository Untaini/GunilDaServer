package com.walkingtalking.gunilda.exercise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = { @Index(columnList = "userId") })
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long courseId;


    @Column(nullable = false)
    private Integer minStride;

    @Column(nullable = false)
    private Integer maxStride;

    @Column(nullable = false)
    private Integer averageStride;


    @Column(nullable = false)
    private Double minSpeed;

    @Column(nullable = false)
    private Double maxSpeed;

    @Column(nullable = false)
    private Double averageSpeed;


    @Column(nullable = false)
    private Integer step;


    @Column(nullable = false)
    private Double stability;


    @Column(nullable = false)
    private Integer distance;


    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp endTime;


}
