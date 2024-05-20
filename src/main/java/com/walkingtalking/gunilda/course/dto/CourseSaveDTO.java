package com.walkingtalking.gunilda.course.dto;

import com.walkingtalking.gunilda.course.entity.Course;
import lombok.Builder;

import java.util.List;

public class CourseSaveDTO {


    @Builder
    public record Command(Long userId,
                          List<CoursePointDTO> course,
                          Boolean doShareCourse,
                          String courseName) {

        public Course toEntity() {
            return Course.builder()
                    .doShare(doShareCourse)
                    .name(courseName)
                    .build();
        }
    }

    @Builder
    public record Response(Long courseId) {

    }


}
