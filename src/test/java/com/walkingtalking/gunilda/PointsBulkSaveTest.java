package com.walkingtalking.gunilda;

import com.walkingtalking.gunilda.course.entity.CoursePoint;
import com.walkingtalking.gunilda.course.repository.CoursePointRepository;
import com.walkingtalking.gunilda.course.repository.CoursePointSaveRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest(showSql = false)
public class PointsBulkSaveTest {

    private static final int COUNT = 10_000;
    private CoursePointRepository pointRepository;
    private CoursePointSaveRepository pointSaveRepository;

    @Autowired
    PointsBulkSaveTest(CoursePointRepository pointRepository, JdbcTemplate jdbcTemplate) {
        this.pointRepository = pointRepository;
        this.pointSaveRepository = new CoursePointSaveRepository(jdbcTemplate);
    }


    @Test
    @DisplayName("normal insert")
    void 일반_insert() {
        long startTime = System.currentTimeMillis();
        List<CoursePoint> points = new ArrayList<>();
        for (int i = 1; i <= COUNT; i++) {
            points.add(CoursePoint.builder()
                    .courseId(0L)
                    .courseOrder(i)
                    .latitude(0.0)
                    .longitude(0.0)
                    .build());
        }
        pointRepository.saveAll(points);
        long endTime = System.currentTimeMillis();
        System.out.println("---------------------------------");
        System.out.printf("수행시간: %d\n", endTime - startTime);
        System.out.println("---------------------------------");
    }

    @Test
    @DisplayName("bulk insert")
    void 벌크_insert() {
        long startTime = System.currentTimeMillis();
        List<CoursePoint> points = new ArrayList<>();
        for (int i = 1; i <= COUNT; i++) {
            points.add(CoursePoint.builder()
                    .courseId(0L)
                    .courseOrder(i)
                    .latitude(0.0)
                    .longitude(0.0)
                    .build());
        }
        pointSaveRepository.saveAll(points);
        long endTime = System.currentTimeMillis();
        System.out.println("---------------------------------");
        System.out.printf("수행시간: %d\n", endTime - startTime);
        System.out.println("---------------------------------");
    }
}
