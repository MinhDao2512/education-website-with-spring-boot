package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.entity.*;
import com.toilamdev.stepbystep.enums.ImageType;
import com.toilamdev.stepbystep.enums.MediaStatus;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.*;
import com.toilamdev.stepbystep.service.IMediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService implements IMediaService {
    private static final long MAX_FILE_SIZE = 500 * 1024 * 1024; // Giới hạn 500MB

    private final LectureRepository lectureRepository;
    private final VideoRepository videoRepository;
    private final GoogleDriveService googleDriveService;
    private final CourseRepository courseRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public String handleUploadFile(MultipartFile file, String mediaType, Integer id) {
        log.info("Bắt đầu thêm file");

        try {
            validateFile(file);

            String originalFileName = file.getOriginalFilename();
            String fileName = generateUniqueFileName(originalFileName);

            return switch (mediaType) {
                case "video-lecture" -> uploadVideo(file, fileName, id);
                case "image-course-thumbnail" -> uploadCourseThumbnail(file, fileName, id);
                case "image-post-thumbnail" -> uploadPostThumbnail(file, fileName, id);
                default -> uploadUserAvatar(file, fileName, id);
            };
        } catch (Exception e) {
            log.error("Có lỗi xảy ra trong quá trình upload file: {}", e.getMessage(), e);
            throw new RuntimeException("Lỗi khi upload file: " + e.getMessage(), e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new GlobalException.FileInputIsNotEmptyException("File không được để trống");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new GlobalException.FileTooLargeException("File vượt quá dung lượng cho phép");
        }
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new GlobalException.FileInputIsNotEmptyException("Tên file không hợp lệ");
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();
        int randomNum = new Random().nextInt(10000);
        return uuid + "_" + randomNum + "_" + timestamp + "_" + sanitizeFileName(originalFileName);
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.\\-_]", "_");
    }

    private String uploadVideo(MultipartFile file, String fileName, Integer id) throws IOException {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(
                () -> new GlobalException.LectureNotFoundException("Không tìm thấy lecture phù hợp")
        );

        Video video = videoRepository.save(Video.builder()
                .title(fileName)
                .lecture(lecture)
                .quality("Full HD-1080")
                .isPreview(true)
                .size(file.getSize())
                .status(MediaStatus.UPLOADING)
                .build());

        String driveFileId = googleDriveService.uploadFile(file, fileName);

        video.setDriveFileId(driveFileId);
        video.setStatus(MediaStatus.READY);
        video.setViewUrl(googleDriveService.getViewUrl(driveFileId));

        video = videoRepository.save(video);

        log.info("Upload video thành công");
        return video.getViewUrl();
    }

    private String uploadCourseThumbnail(MultipartFile file, String fileName, Integer id) throws IOException {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new GlobalException.CourseNotFoundException("Không tìm thấy Course phù hợp")
        );

        Image image = imageRepository.save(Image.builder()
                .title(fileName)
                .size(file.getSize())
                .course(course)
                .status(MediaStatus.UPLOADING)
                .type(ImageType.COURSE_THUMBNAIL)
                .build());

        String driveFileId = googleDriveService.uploadFile(file, fileName);

        image.setStatus(MediaStatus.READY);
        image.setViewUrl(googleDriveService.getViewUrl(driveFileId));

        image = imageRepository.save(image);

        course.setThumbnail(image);
        this.courseRepository.save(course);

        log.info("Upload file thành công");
        return image.getViewUrl();
    }

    private String uploadPostThumbnail(MultipartFile file, String fileName, Integer id) throws IOException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new GlobalException.PostNotFoundException("Không tìm thấy Post phù hợp")
        );

        Image image = imageRepository.save(Image.builder()
                .title(fileName)
                .size(file.getSize())
                .post(post)
                .status(MediaStatus.UPLOADING)
                .type(ImageType.POST_THUMBNAIL)
                .build());

        String driveFileId = googleDriveService.uploadFile(file, fileName);

        image.setStatus(MediaStatus.READY);
        image.setViewUrl(googleDriveService.getViewUrl(driveFileId));

        image = imageRepository.save(image);

        post.setThumbnail(image);
        this.postRepository.save(post);

        log.info("Upload file thành công");
        return image.getViewUrl();
    }

    private String uploadUserAvatar(MultipartFile file, String fileName, Integer id) throws IOException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new GlobalException.UserNotFoundException("Không tìm thấy User phù hợp")
        );

        Image image = imageRepository.save(Image.builder()
                .title(fileName)
                .size(file.getSize())
                .user(user)
                .status(MediaStatus.UPLOADING)
                .type(ImageType.AVATAR)
                .build());

        String driveFileId = googleDriveService.uploadFile(file, fileName);

        image.setStatus(MediaStatus.READY);
        image.setViewUrl(googleDriveService.getViewUrl(driveFileId));

        image = imageRepository.save(image);

        user.setAvatar(image);
        this.userRepository.save(user);

        log.info("Upload file thành công");
        return image.getViewUrl();
    }
}
