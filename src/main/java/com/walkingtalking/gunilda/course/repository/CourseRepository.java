package com.walkingtalking.gunilda.course.repository;

import com.walkingtalking.gunilda.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
