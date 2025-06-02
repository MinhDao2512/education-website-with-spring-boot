package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.SectionRequestDTO;
import com.toilamdev.stepbystep.entity.Course;
import com.toilamdev.stepbystep.entity.Section;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.CourseRepository;
import com.toilamdev.stepbystep.repository.SectionRepository;
import com.toilamdev.stepbystep.service.ISectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService implements ISectionService {
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;

    @Override
    public Integer addNewSection(Integer courseId, SectionRequestDTO sectionRequestDTO) {
        log.info("Bắt đầu tạo mới Section với thông tin: {}", sectionRequestDTO);
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new GlobalException.CourseNotFoundException("Không tìm thấy course tương ứng"));

            Section section = Section.builder()
                    .title(sectionRequestDTO.getTitle())
                    .objective(sectionRequestDTO.getObjective())
                    .sectionOrder(sectionRequestDTO.getSectionOrder())
                    .course(course)
                    .build();

            section = sectionRepository.save(section);
            log.info("Thêm mới Section thành công với ID: {}", section.getId());

            return section.getId();
        } catch (GlobalException.CourseNotFoundException e) {
            log.error("Có lỗi xảy ra khi thêm mới Section: {}", e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public void modifierSection(Integer sectionId, SectionRequestDTO sectionRequestDTO) {
        log.info("Bắt đầu cập nhật thông tin cho Section với ID: {}", sectionId);
        try {
            Section section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new GlobalException.SectionNotFoundException(
                            String.format("Không tìm thấy Section với Id = %d", sectionId)));

            section.setTitle(sectionRequestDTO.getTitle());
            section.setObjective(sectionRequestDTO.getObjective());
            section.setSectionOrder(sectionRequestDTO.getSectionOrder());

            sectionRepository.save(section);
            log.info("Cập nhật thành công Section có Id = {}", sectionId);
        } catch (Exception e) {
            log.error("Có lỗi xảy ra khi update Section");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteSection(Integer sectionId) {
        log.info("Bắt đầu xóa Section với ID: {}", sectionId);
        try {
            Section section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new GlobalException.SectionNotFoundException("Không tìm thấy Section tương ứng"));

            section.setDeleted(true);

            sectionRepository.save(section);
            log.info("Xóa section thành công với ID: {}", sectionId);
        } catch (Exception e) {
            log.error("Có lỗi xảy ra khi xóa Section với ID: {}", sectionId, e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
