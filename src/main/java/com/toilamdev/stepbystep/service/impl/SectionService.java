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
    public Integer addNewSection(SectionRequestDTO sectionRequestDTO) {
        log.info("Bắt đầu tạo mới Section");
        try{
            Course course = this.courseRepository.findById(sectionRequestDTO.getCourseId()).orElseThrow(
                    () -> new GlobalException.CourseNotFoundException("Không tìn thấy course tương ứng")
            );

            Section section = Section.builder()
                    .title(sectionRequestDTO.getSectionTitle())
                    .objective(sectionRequestDTO.getObjective())
                    .course(course)
                    .build();

            section = this.sectionRepository.save(section);

            log.info("Thêm mới Section thành công");

            return section.getId();
        }catch (GlobalException.CourseNotFoundException e){
            log.error("Có lỗi xảy ra khi thêm mới Section: {}", e.getMessage(), e);
            throw new RuntimeException();
        }
    }
}
