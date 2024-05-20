package com.walkingtalking.gunilda.course.dto;

import com.walkingtalking.gunilda.course.entity.Course;
import lombok.Builder;

import java.util.List;

public class CourseSearchDTO {

    @Builder
    public record SearchCommand(List<Long> courseIds) {

    }

    @Builder
    public record MyCoursesResponse(List<MyCourse> courses) {

    }

    @Builder
    public record MyCourse(Long courseId, Boolean doShare, String courseName, List<CoursePointDTO> coursePoints) {

        public static MyCourse from(Course course) {
            List<CoursePointDTO> coursePoints = course.getCoursePoints()
                    .stream()
                    .map(CoursePointDTO::from)
                    .toList();

            return MyCourse.builder()
                    .courseId(course.getId())
                    .doShare(course.getDoShare())
                    .courseName(course.getName())
                    .coursePoints(coursePoints)
                    .build();
        }
    }
}
