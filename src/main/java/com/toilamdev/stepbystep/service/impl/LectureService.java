package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.constant.TypeFileUpload;
import com.toilamdev.stepbystep.dto.request.LectureRequestDTO;
import com.toilamdev.stepbystep.entity.Lecture;
import com.toilamdev.stepbystep.entity.Section;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.LectureRepository;
import com.toilamdev.stepbystep.repository.SectionRepository;
import com.toilamdev.stepbystep.repository.VideoRepository;
import com.toilamdev.stepbystep.service.ILectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureService implements ILectureService {
    private final LectureRepository lectureRepository;
    private final SectionRepository sectionRepository;
    private final VideoRepository videoRepository;
    private final MediaService mediaService;

    @Override
    public Integer addNewLecture(Integer sectionId, LectureRequestDTO lectureRequestDTO) {
        log.info("Bắt đầu thêm mới Lecture");
        try {
            Section section = this.sectionRepository.findById(sectionId).orElseThrow(
                    () -> new GlobalException.SectionNotFoundException("Không tìm thấy Section phù hợp")
            );

            Lecture lecture = Lecture.builder()
                    .title(lectureRequestDTO.getTitle())
                    .document(lectureRequestDTO.getDocument())
                    .lectureOrder(lectureRequestDTO.getLectureOrder())
                    .section(section)
                    .build();

            lecture = this.lectureRepository.save(lecture);

            log.info("Thêm mới lecture thành công");
            return lecture.getId();
        } catch (GlobalException.SectionNotFoundException e) {
            log.error("Có lỗi xảy ra khi thêm mới Lecture: {}", e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public void modifierLecture(Integer lectureId, LectureRequestDTO lectureRequestDTO) {
        log.info("Bắt đầu cập nhật lecture");
        try {
            Lecture lecture = this.lectureRepository.findById(lectureId).orElseThrow(
                    () -> new GlobalException.LectureNotFoundException("Không tìm thấy Lecture phù hợp")
            );

            lecture.setTitle(lectureRequestDTO.getTitle());
            lecture.setDocument(lectureRequestDTO.getDocument());
            lecture.setLectureOrder(lectureRequestDTO.getLectureOrder());

            this.lectureRepository.save(lecture);
            log.info("Cập nhật lecture thành công");
        } catch (Exception e) {
            log.error("Có lỗi xảy ra khi update Lecture");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteLecture(Integer lectureId) {
        log.info("Bắt đầu xóa Lecture");
        try {
            Lecture lecture = this.lectureRepository.findById(lectureId).orElseThrow(
                    () -> new GlobalException.LectureNotFoundException("Không tìm thấy Lecture tương ứng")
            );

            lecture.setDeleted(true);

            this.lectureRepository.save(lecture);
            log.info("Xóa lecture thành công");
        } catch (Exception e) {
            log.error("Có lỗi xảy ra khi xóa Lecture");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String modifierVideoOfLecture(Integer lectureId, MultipartFile video) {
        log.info("Bắt đầu thực hiện cập nhật video mới");
        try{
            Lecture currentLecture = this.lectureRepository.findById(lectureId).orElseThrow(
                    () -> new GlobalException.LectureNotFoundException("Không tìm thấy Lecture tương ứng")
            );
            //Before delete video in database ==> Delete video on Google Driver
            this.mediaService.handleDeleteFile(currentLecture.getVideo().getDriveFileId());
            //After delete video on Google Driver ==> Delete video in database
            this.videoRepository.deleteById(currentLecture.getVideo().getId());
            //Update new Video

            return this.mediaService.handleUploadFile(video, TypeFileUpload.VIDEO_LECTURE, lectureId);
        }catch(Exception exception){
            log.error("Cập nhật video không thành công");
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public void deleteVideoOfLecture(Integer lectureId) {
        log.info("Bắt đầu thực hiện xóa video");
        try{
            Lecture currentLecture = this.lectureRepository.findById(lectureId).orElseThrow(
                    () -> new GlobalException.LectureNotFoundException("Không tìm thấy lecture tương ứng")
            );
            //Before delete video in database ==> Delete video on Google Driver
            this.mediaService.handleDeleteFile(currentLecture.getVideo().getDriveFileId());
            //After delete video on Google Driver ==> Delete video in database
            this.videoRepository.deleteById(currentLecture.getVideo().getId());

            log.info("Xóa thành công video của lecture có id: {}", lectureId);
        }catch(Exception exception){
            log.error("Có lỗi xảy ra trong quá trình xóa");
            throw new RuntimeException(exception.getMessage());
        }
    }
}
