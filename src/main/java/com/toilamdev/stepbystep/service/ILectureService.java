package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ILectureService {
    Integer addNewLecture(Integer sectionId, LectureRequestDTO lectureRequestDTO);

    void modifierLecture(Integer lectureId, LectureRequestDTO lectureRequestDTO);

    void deleteLecture(Integer lectureId);

    String modifierVideoOfLecture(Integer lectureId, MultipartFile video);

    void deleteVideoOfLecture(Integer lectureId);
}
