package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import com.toilamdev.stepbystep.entity.Lecture;
import com.toilamdev.stepbystep.entity.Section;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.LectureRepository;
import com.toilamdev.stepbystep.repository.SectionRepository;
import com.toilamdev.stepbystep.service.ILectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureService implements ILectureService {
    private final LectureRepository lectureRepository;
    private final SectionRepository sectionRepository;

    @Override
    public Integer addNewLecture(LectureRequestDTO lectureRequestDTO) {
        log.info("Bắt đầu thêm mới Lecture");
        try{
            Section section = this.sectionRepository.findById(lectureRequestDTO.getSectionId()).orElseThrow(
                    () -> new GlobalException.SectionNotFoundException("Không tìm thấy Section phù hợp")
            );

            Lecture lecture = Lecture.builder()
                    .title(lectureRequestDTO.getLectureTitle())
                    .document(lectureRequestDTO.getDocument())
                    .section(section)
                    .build();

            lecture = this.lectureRepository.save(lecture);

            log.info("Thêm mới lecture thành công");
            return lecture.getId();
        }catch (GlobalException.SectionNotFoundException e){
            log.error("Có lỗi xảy ra khi thêm mới Lecture: {}", e.getMessage(), e);
            throw new RuntimeException();
        }
    }
}
