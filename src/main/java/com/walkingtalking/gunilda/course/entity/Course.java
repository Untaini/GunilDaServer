package com.walkingtalking.gunilda.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private Boolean doShare;

    @Column(nullable = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "courseId")
    private Set<CoursePoint> coursePoints;

    public List<CoursePoint> getCoursePoints() {
        return coursePoints.stream()
                .sorted(Comparator.comparing(CoursePoint::getCourseOrder))
                .toList();
    }
}
