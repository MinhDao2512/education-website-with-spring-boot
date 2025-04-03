package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.CourseRequestDTO;
import com.toilamdev.stepbystep.entity.Course;

public interface ICourseService {
    int createNewCourse(CourseRequestDTO courseRequestDTO);
}
