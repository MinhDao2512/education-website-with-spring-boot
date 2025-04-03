package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.dto.response.ApiResponseDTO;
import com.toilamdev.stepbystep.service.impl.MediaService;
import com.toilamdev.stepbystep.service.impl.ResponseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
@RequestMapping("${api.version}/medias")
@RequiredArgsConstructor
@Tag(name = "Media Controller")
public class MediaController {
    private final ResponseService responseService;
    private final MediaService mediaService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO> uploadVideo(HttpServletRequest request,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("mediaType") String mediaType,
                                                      @RequestParam("id") Integer id) {
        try {
            return this.responseService.success(HttpStatus.CREATED, "Upload file success",
                    Collections.singletonMap("viewUrl", this.mediaService.handleUploadFile(file, mediaType, id)));
        } catch (RuntimeException e) {
            return this.responseService.fail(request, HttpStatus.INTERNAL_SERVER_ERROR, "Upload file fail",
                    Collections.singletonMap("message_error", e.getMessage()));
        }
    }
}
