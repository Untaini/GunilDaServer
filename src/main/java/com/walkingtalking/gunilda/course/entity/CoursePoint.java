package com.walkingtalking.gunilda.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = { @Index(columnList = "courseId") }, uniqueConstraints = { @UniqueConstraint(columnNames = {"courseId", "courseOrder"}) })
public class CoursePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private Integer courseOrder;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

}
