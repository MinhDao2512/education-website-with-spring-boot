package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.SectionRequestDTO;

public interface ISectionService {
    Integer addNewSection(Integer courseId, SectionRequestDTO sectionRequestDTO);

    void modifierSection(Integer sectionId, SectionRequestDTO sectionRequestDTO);

    void deleteSection(Integer sectionId);
}
