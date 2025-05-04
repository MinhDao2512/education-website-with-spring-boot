package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.request.LectureUpdateRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.LectureService;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/lectures")
@Tag(name = "Lecture Controller")
@RequiredArgsConstructor
public class LectureController {
    private final ResponseService responseService;
    private final LectureService lectureService;

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateLecture(HttpServletRequest request, @PathVariable("id") Integer lectureId,
                                                        @Valid @RequestBody LectureUpdateRequestDTO lectureUpdateRequestDTO) {
        try {
            this.lectureService.modifierLecture(lectureId, lectureUpdateRequestDTO);
            return this.responseService.success(HttpStatus.NO_CONTENT, "Update lecture success");
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Update lecture fail");
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseDTO> deleteLecture(HttpServletRequest request, @RequestParam("id") Integer lectureId) {
        try {
            this.lectureService.deleteLecture(lectureId);
            return this.responseService.success(HttpStatus.NO_CONTENT, "Delete lecture success");
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Delete lecture fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }
}
