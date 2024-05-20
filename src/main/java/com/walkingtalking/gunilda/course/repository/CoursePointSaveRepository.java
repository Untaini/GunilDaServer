package com.walkingtalking.gunilda.course.repository;

import com.walkingtalking.gunilda.course.entity.CoursePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CoursePointSaveRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<CoursePoint> points) {
        String sql = "INSERT INTO course_point (course_id, course_order, latitude, longitude)"
                + "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, points, points.size(), (ps, point) -> {
            ps.setLong(1, point.getCourseId());
            ps.setInt(2, point.getCourseOrder());
            ps.setDouble(3, point.getLatitude());
            ps.setDouble(4, point.getLongitude());
        });
    }
}
