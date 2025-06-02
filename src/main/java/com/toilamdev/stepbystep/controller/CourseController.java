package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.ApiResponseExample;
import com.toilamdev.stepbystep.dto.request.CourseRequestDTO;
import com.toilamdev.stepbystep.dto.request.SectionRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.CourseService;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import com.toilamdev.stepbystep.service.impl.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/courses")
@Tag(name = "Course Controller")
@RequiredArgsConstructor
@Validated
public class CourseController {
    private final ResponseService responseService;
    private final CourseService courseService;
    private final SectionService sectionService;

    @Operation(summary = "Create new course", description = "Add new course")
    @ApiResponses(value = {
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.CREATED_201)
            )),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.BAD_REQUEST_400)
            ))
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO> addNewCourse(HttpServletRequest request,
                                                       @RequestPart("thumbnailFile") MultipartFile thumbnailFile,
                                                       @Valid @RequestPart("courseRequestData") CourseRequestDTO courseRequestDTO) {
        try {
            return this.responseService.success(HttpStatus.CREATED,"Create new course success",
                    Collections.singletonMap("courseId", this.courseService.createNewCourse(courseRequestDTO, thumbnailFile)));
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Create new course fail",
                    Collections.singletonMap("message_detail", e.getMessage()));
        }
    }

    @PostMapping("/{id}/section")
    public ResponseEntity<ApiResponseDTO> addNewSection(HttpServletRequest request,
                                                        @PathVariable("id") @Min(1) Integer courseId,
                                                        @Valid @RequestBody SectionRequestDTO sectionRequestDTO) {
        try {
            return this.responseService.success(HttpStatus.CREATED, "Create new section success",
                    Collections.singletonMap("section_id", this.sectionService.addNewSection(courseId,
                            sectionRequestDTO)));
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Create new section fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }
}
