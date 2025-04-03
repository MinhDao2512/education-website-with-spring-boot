package com.toilamdev.stepbystep.entity;

import com.toilamdev.stepbystep.enums.ImageType;
import com.toilamdev.stepbystep.enums.MediaStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image extends BaseEntity{
    @Column(nullable = false)
    private String title;

    private String viewUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Enumerated(EnumType.STRING)
    private MediaStatus status;

    private Long size;

    @OneToOne(mappedBy = "avatar")
    private User user;

    @OneToOne(mappedBy = "thumbnail")
    private Course course;

    @OneToOne(mappedBy = "thumbnail")
    private Post post;
}
