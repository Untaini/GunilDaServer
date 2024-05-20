package com.walkingtalking.gunilda.course.dto;

import com.walkingtalking.gunilda.course.entity.CoursePoint;
import lombok.Builder;

@Builder
public record CoursePointDTO(Double latitude, Double longitude) {
    public CoursePoint toEntity(Long courseId, Integer order) {
        return CoursePoint.builder()
                .courseId(courseId)
                .courseOrder(order)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static CoursePointDTO from(CoursePoint coursePoint) {
        return CoursePointDTO.builder()
                .latitude(coursePoint.getLatitude())
                .longitude(coursePoint.getLongitude())
                .build();
    }

}