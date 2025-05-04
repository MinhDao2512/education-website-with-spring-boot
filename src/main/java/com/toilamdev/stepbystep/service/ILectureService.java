package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import com.toilamdev.stepbystep.dto.request.LectureUpdateRequestDTO;

public interface ILectureService {
    Integer addNewLecture(Integer sectionId, LectureRequestDTO lectureRequestDTO);

    void modifierLecture(Integer lectureId, LectureUpdateRequestDTO lectureUpdateRequestDTO);

    void deleteLecture(Integer lectureId);
}
