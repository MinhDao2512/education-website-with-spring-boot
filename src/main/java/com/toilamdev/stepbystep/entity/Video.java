package com.toilamdev.stepbystep.entity;

import com.toilamdev.stepbystep.enums.MediaStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video extends BaseEntity{
    private String title;
    private String quality;
    private String driveFileId;
    private String viewUrl;
    private Long duration;
    private boolean isPreview;
    private Long size;

    @Enumerated(EnumType.STRING)
    private MediaStatus status;

    @OneToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
