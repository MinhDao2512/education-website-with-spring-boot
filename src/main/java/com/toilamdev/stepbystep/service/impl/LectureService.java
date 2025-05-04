package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import com.toilamdev.stepbystep.dto.request.LectureUpdateRequestDTO;
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
    public Integer addNewLecture(Integer sectionId, LectureRequestDTO lectureRequestDTO) {
        log.info("Bắt đầu thêm mới Lecture");
        try {
            Section section = this.sectionRepository.findById(sectionId).orElseThrow(
                    () -> new GlobalException.SectionNotFoundException("Không tìm thấy Section phù hợp")
            );

            Lecture lecture = Lecture.builder()
                    .title(lectureRequestDTO.getTitle())
                    .document(lectureRequestDTO.getDocument())
                    .orderOfLecture(lectureRequestDTO.getOrderOfLecture())
                    .section(section)
                    .build();

            lecture = this.lectureRepository.save(lecture);

            log.info("Thêm mới lecture thành công");
            return lecture.getId();
        } catch (GlobalException.SectionNotFoundException e) {
            log.error("Có lỗi xảy ra khi thêm mới Lecture: {}", e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public void modifierLecture(Integer lectureId, LectureUpdateRequestDTO lectureUpdateRequestDTO) {
        log.info("Bắt đầu cập nhật lecture");
        try {
            Lecture lecture = this.lectureRepository.findById(lectureId).orElseThrow(
                    () -> new GlobalException.LectureNotFoundException("Không tìm thấy Lecture phù hợp")
            );

            lecture.setTitle(lectureUpdateRequestDTO.getLectureTitle());
            lecture.setDocument(lectureUpdateRequestDTO.getDocument());

            this.lectureRepository.save(lecture);
            log.info("Cập nhật lecture thành công");
        } catch (Exception e) {
            log.error("Có lỗi xảy ra khi update Lecture");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteLecture(Integer lectureId) {
        log.info("Bắt đầu xóa Lecture");
        try {
            Lecture lecture = this.lectureRepository.findById(lectureId).orElseThrow(
                    () -> new GlobalException.LectureNotFoundException("Không tìm thấy Lecture tương ứng")
            );

            lecture.setDeleted(true);

            this.lectureRepository.save(lecture);
            log.info("Xóa lecture thành công");
        } catch (Exception e) {
            log.error("Có lỗi xảy ra khi xóa Lecture");
            throw new RuntimeException(e.getMessage());
        }
    }
}
