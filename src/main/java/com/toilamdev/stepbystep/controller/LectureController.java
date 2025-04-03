package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.LectureService;
import com.toilamdev.stepbystep.service.impl.ResponseService;
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
@RequestMapping("${api.version}/lectures")
@Tag(name = "Lecture Controller")
@RequiredArgsConstructor
public class LectureController {
    private final ResponseService responseService;
    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO> createNewLecture(HttpServletRequest request, @Valid @RequestBody LectureRequestDTO lectureRequestDTO){
        try {
            return this.responseService.success(HttpStatus.CREATED, "Create new lecture success",
                    Collections.singletonMap("lecture_id", this.lectureService.addNewLecture(lectureRequestDTO)));
        } catch (RuntimeException e){
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Create new lecture fail");
        }
    }
}
