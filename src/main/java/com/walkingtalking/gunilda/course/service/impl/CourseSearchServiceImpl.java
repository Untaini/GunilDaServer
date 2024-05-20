package com.walkingtalking.gunilda.course.service.impl;

import com.walkingtalking.gunilda.course.dto.CourseSearchDTO;
import com.walkingtalking.gunilda.course.entity.Course;
import com.walkingtalking.gunilda.course.repository.CourseRepository;
import com.walkingtalking.gunilda.course.service.CourseSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourseSearchServiceImpl implements CourseSearchService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public CourseSearchDTO.MyCoursesResponse searchMyCourses(CourseSearchDTO.SearchCommand command) {
        List<Course> courses = courseRepository.findAllById(command.courseIds());

        List<CourseSearchDTO.MyCourse> myCourses = courses.stream()
                .map(CourseSearchDTO.MyCourse::from)
                .toList();

        return CourseSearchDTO.MyCoursesResponse.builder()
                .courses(myCourses)
                .build();
    }
}
