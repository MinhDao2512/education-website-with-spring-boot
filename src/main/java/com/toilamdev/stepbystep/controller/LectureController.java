package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.constant.TypeFileUpload;
import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.LectureService;
import com.toilamdev.stepbystep.service.impl.MediaService;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/lectures")
@Tag(name = "Lecture Controller")
@RequiredArgsConstructor
public class LectureController {
    private final ResponseService responseService;
    private final LectureService lectureService;
    private final MediaService mediaService;

    @PostMapping("/{id}/video")
    public ResponseEntity<ApiResponseDTO> addNewVideo(HttpServletRequest request, @PathVariable("id") Integer lectureId,
                                                      @RequestParam("new-video")MultipartFile video){
        try{
            String linkedVideo = mediaService.handleUploadFile(video, TypeFileUpload.VIDEO_LECTURE, lectureId);
            return responseService.success(HttpStatus.CREATED, "Add new video success",
                    Collections.singletonMap("linked-video", linkedVideo));
        }catch(RuntimeException exception){
            return responseService.fail(request, HttpStatus.CONFLICT, "Add new video fail", Collections.singletonMap(
                    "message-error", exception.getMessage()
            ));
        }
    }

    @PutMapping("/{id}/video")
    public ResponseEntity<ApiResponseDTO> changeVideo(HttpServletRequest request, @PathVariable("id") Integer lectureId,
                                                      @RequestParam("video") MultipartFile video){
        try{
            String newLinkedVideo = this.lectureService.modifierVideoOfLecture(lectureId, video);
            return responseService.success(HttpStatus.OK, String.format("Update new video for Lecture has id: %d success", lectureId),
                    Collections.singletonMap("linked-video", newLinkedVideo));
        }catch(RuntimeException exception){
            return responseService.fail(request, HttpStatus.CONFLICT, String.format("Update new video for Lecture has id: %d success", lectureId));
        }
    }

    @DeleteMapping("/{id}/video")
    public ResponseEntity<ApiResponseDTO> deleteVideo(HttpServletRequest request, @PathVariable("id") Integer lectureId){
        try{
            this.lectureService.deleteVideoOfLecture(lectureId);
            return this.responseService.success(HttpStatus.NO_CONTENT, "Delete video success");
        }catch(RuntimeException exception){
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Delete video fail");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateLecture(HttpServletRequest request, @PathVariable("id") Integer lectureId,
                                                        @Valid @RequestBody LectureRequestDTO lectureRequestDTO){
        try{
            this.lectureService.modifierLecture(lectureId, lectureRequestDTO);
            return this.responseService.success(HttpStatus.NO_CONTENT, "Update lecture success");
        }catch(RuntimeException exception){
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Update lecture fail");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteLecture(HttpServletRequest request, @PathVariable("id") Integer lectureId){
        try{
            this.lectureService.deleteLecture(lectureId);
            return this.responseService.success(HttpStatus.NO_CONTENT, "Delete lecture success");
        }catch(RuntimeException exception){
            return this.responseService.fail(request, HttpStatus.CONFLICT, "Delete lecture fail");
        }
    }
}
