package com.walkingtalking.gunilda.course.service.impl;

import com.walkingtalking.gunilda.course.dto.CourseSaveDTO;
import com.walkingtalking.gunilda.course.entity.Course;
import com.walkingtalking.gunilda.course.entity.CoursePoint;
import com.walkingtalking.gunilda.course.repository.CoursePointSaveRepository;
import com.walkingtalking.gunilda.course.repository.CourseRepository;
import com.walkingtalking.gunilda.course.service.CourseSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseSaveServiceImpl implements CourseSaveService {

    private final CourseRepository courseRepository;
    private final CoursePointSaveRepository coursePointSaveRepository;

    @Override
    @Transactional
    public CourseSaveDTO.Response save(CourseSaveDTO.Command command) {
        Course course = command.toEntity();

        courseRepository.save(course);

        List<CoursePoint> points = new ArrayList<>();
        for (int index = 0; index < command.course().size(); index++) {
            points.add(command.course().get(index).toEntity(course.getId(), index));
        }

        coursePointSaveRepository.saveAll(points);

        return CourseSaveDTO.Response.builder()
                .courseId(course.getId())
                .build();
    }
}
