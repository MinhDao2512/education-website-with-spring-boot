package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.CourseRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ICourseService {
    int createNewCourse(CourseRequestDTO courseRequestDTO, MultipartFile thumbnail);
}
