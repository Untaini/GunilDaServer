package com.walkingtalking.gunilda.course.repository;

import com.walkingtalking.gunilda.course.entity.CoursePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePointRepository extends JpaRepository<CoursePoint, Long> {


}
