package com.walkingtalking.gunilda.course.service;

import com.walkingtalking.gunilda.course.dto.CourseSearchDTO;

public interface CourseSearchService {

    CourseSearchDTO.MyCoursesResponse searchMyCourses(CourseSearchDTO.SearchCommand command);

}
