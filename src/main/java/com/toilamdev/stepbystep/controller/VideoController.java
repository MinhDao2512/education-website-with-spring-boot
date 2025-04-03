package com.toilamdev.stepbystep.controller;

import com.toilamdev.stepbystep.service.impl.GoogleDriveService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("${api.version}/videos")
@RequiredArgsConstructor
@Tag(name = "Video Controller")
public class VideoController {
    private final GoogleDriveService googleDriveService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("title") String title) {
        try {
            // Kiểm tra file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File không được để trống");
            }

            // Kiểm tra định dạng file
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("video/")) {
                return ResponseEntity.badRequest().body("Chỉ chấp nhận file video");
            }
            // Tạo tên file duy nhất
            String fileName = "video_" + "MinhDao" + "_" + file.getOriginalFilename();

            // Upload file lên Google Drive
            String fileId = googleDriveService.uploadFile(file, fileName);

            // Cập nhật thông tin video
            String viewUrl = googleDriveService.getViewUrl(fileId);

            return ResponseEntity.ok(Collections.singletonMap("viewUrl", viewUrl));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi upload video: " + e.getMessage());
        }
    }
}
