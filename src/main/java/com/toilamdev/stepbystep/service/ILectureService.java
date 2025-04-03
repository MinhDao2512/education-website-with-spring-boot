package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;

public interface ILectureService {
    Integer addNewLecture(LectureRequestDTO lectureRequestDTO);
}
