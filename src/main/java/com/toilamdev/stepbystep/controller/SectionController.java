package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.request.SectionRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import com.toilamdev.stepbystep.service.impl.SectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/sections")
@Tag(name = "Section Controller")
@RequiredArgsConstructor
public class SectionController {
    private final ResponseService responseService;
    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO> createNewSection(HttpServletRequest request,
                                                           @Valid @RequestBody SectionRequestDTO sectionRequestDTO) {
        try {
            return this.responseService.success(HttpStatus.CREATED, "Create new section success",
                    Collections.singletonMap("section_id", this.sectionService.addNewSection(sectionRequestDTO)));
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Create new section fail");
        }
    }
}
