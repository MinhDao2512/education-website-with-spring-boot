package com.toilamdev.stepbystep.service;

import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IGoogleDriveService {
    /**
     * Upload một file lên Google Drive.
     *
     * @param multipartFile File cần upload, được cung cấp dưới dạng Spring {@link MultipartFile}.
     * @param fileName      Tên file mong muốn trên Google Drive.
     * @return ID của file đã upload trên Google Drive.
     * @throws IOException Nếu có lỗi trong quá trình upload file.
     */
    String uploadFile(MultipartFile multipartFile, String fileName) throws IOException;

    /**
     * Lấy URL để xem file trên Google Drive.
     *
     * @param fileId ID của file trên Google Drive.
     * @return URL có thể dùng để xem file trong trình duyệt.
     */
    String getViewUrl(String fileId);

    /**
     * Xóa một file khỏi Google Drive.
     *
     * @param fileId ID của file cần xóa.
     * @throws IOException Nếu có lỗi trong quá trình xóa file.
     */
    void deleteFile(String fileId) throws IOException;

    /**
     * Liệt kê tất cả các file video trong Google Drive.
     *
     * @return Danh sách các đối tượng {@link File} đại diện cho các file video.
     * @throws IOException Nếu có lỗi trong quá trình liệt kê file.
     */
    List<File> listVideoFiles() throws IOException;
}