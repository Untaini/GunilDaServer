package com.walkingtalking.gunilda.course.service;

import com.walkingtalking.gunilda.course.dto.CourseSaveDTO;

public interface CourseSaveService {

    CourseSaveDTO.Response save(CourseSaveDTO.Command command);
}
