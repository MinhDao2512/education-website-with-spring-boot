package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.ApiResponseExample;
import com.toilamdev.stepbystep.dto.request.CourseRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.CourseService;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/courses")
@Tag(name = "Course Controller")
@RequiredArgsConstructor
public class CourseController {
    private final ResponseService responseService;
    private final CourseService courseService;

    @Operation(summary = "Create new course", description = "Add new course")
    @ApiResponses(value = {
            @ApiResponse(description = "Created", responseCode = "201", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.CREATED_201)
            )),
            @ApiResponse(description = "Bad request", responseCode = "400", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = ApiResponseExample.BAD_REQUEST_400)
            ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO> addNewCourse(HttpServletRequest request,
                                                       @Valid @RequestBody CourseRequestDTO courseRequestDTO) {
        try{
            return this.responseService.success(HttpStatus.CREATED, "Add new course success",
                    Collections.singletonMap("course_id", this.courseService.createNewCourse(courseRequestDTO)));
        }catch(RuntimeException e){
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Create new course fail",
                    Collections.singletonMap("message_detail", e.getMessage()));
        }
    }
}
