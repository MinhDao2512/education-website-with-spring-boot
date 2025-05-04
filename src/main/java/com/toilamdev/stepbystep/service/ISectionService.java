package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.SectionRequestDTO;
import com.toilamdev.stepbystep.dto.request.SectionUpdateRequestDTO;

public interface ISectionService {
    Integer addNewSection(Integer courseId, SectionRequestDTO sectionRequestDTO);

    void modifierSection(Integer sectionId, SectionUpdateRequestDTO sectionUpdateRequestDTO);

    void deleteSection(Integer sectionId);
}
