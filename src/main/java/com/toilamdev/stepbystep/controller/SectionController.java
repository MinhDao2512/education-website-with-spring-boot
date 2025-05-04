package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import com.toilamdev.stepbystep.dto.request.SectionUpdateRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.LectureService;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import com.toilamdev.stepbystep.service.impl.SectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/sections")
@Tag(name = "Section Controller")
@RequiredArgsConstructor
@Validated
public class SectionController {
    private final ResponseService responseService;
    private final SectionService sectionService;
    private final LectureService lectureService;

    @PostMapping("/{id}/lecture")
    public ResponseEntity<ApiResponseDTO> createNewLecture(HttpServletRequest request,
                                                           @PathVariable("id") @Min(1) Integer sectionId,
                                                           @Valid @RequestBody LectureRequestDTO lectureRequestDTO){
        try{
            return this.responseService.success(HttpStatus.CREATED, "Create new lecture success",
                    Collections.singletonMap("lecture_id", this.lectureService.addNewLecture(sectionId, lectureRequestDTO)));
        }catch (RuntimeException e){
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Create new lecture fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateSection(HttpServletRequest request, @PathVariable("id") Integer sectionId,
                                                        @Valid @RequestBody SectionUpdateRequestDTO sectionUpdateRequestDTO) {
        try {
            this.sectionService.modifierSection(sectionId, sectionUpdateRequestDTO);
            return this.responseService.success(HttpStatus.NO_CONTENT, "Update section success");
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Update section fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseDTO> deleteSection(HttpServletRequest request, @RequestParam("id") Integer sectionId) {
        try {
            this.sectionService.deleteSection(sectionId);
            return this.responseService.success(HttpStatus.NO_CONTENT, "Delete section success");
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Delete section fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }
}
