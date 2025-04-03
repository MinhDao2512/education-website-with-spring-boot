package com.toilamdev.stepbystep.service.impl;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleDriveService {
    private final Drive driveService;
    private static final String VIDEO_FOLDER_ID = "1AjoZsyN-mVUkiGJMwAyOdx_XcC06O_EA";

    /**
     * Upload file lên Google Drive
     */
    public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        // Tạo metadata cho file
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(VIDEO_FOLDER_ID));

        // Chuẩn bị content từ MultipartFile
        InputStreamContent mediaContent = new InputStreamContent(
                multipartFile.getContentType(),
                new ByteArrayInputStream(multipartFile.getBytes())
        );

        // Upload file lên Drive
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        log.info("File ID: " + uploadedFile.getId());

        // Cấp quyền xem cho bất kỳ ai có link
        setFilePublic(uploadedFile.getId());

        return uploadedFile.getId();
    }

    /**
     * Cấp quyền xem cho file
     */
    private void setFilePublic(String fileId) throws IOException {
        Permission permission = new Permission();
        permission.setType("anyone");
        permission.setRole("reader");

        driveService.permissions().create(fileId, permission).execute();
    }

    /**
     * Lấy URL để xem file
     */
    public String getViewUrl(String fileId) {
        return "https://drive.google.com/file/d/" + fileId + "/view";
    }

    /**
     * Xóa file khỏi Drive
     */
    public void deleteFile(String fileId) throws IOException {
        driveService.files().delete(fileId).execute();
    }

    /**
     * Lấy danh sách video trong folder
     */
    public List<File> listVideoFiles() throws IOException {
        String query = "'" + VIDEO_FOLDER_ID + "' in parents and mimeType contains 'video/'";

        FileList result = driveService.files().list()
                .setQ(query)
                .setPageSize(100)
                .setFields("nextPageToken, files(id, name, webViewLink)")
                .execute();

        return result.getFiles();
    }
}
