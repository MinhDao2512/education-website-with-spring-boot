package com.toilamdev.stepbystep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "lectures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture extends BaseEntity {
    @Column(nullable = false)
    private String title;

    private String video;
    private String document;
    private float totalTime;

    @Column(nullable = false)
    private boolean isPreview;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private Set<Comment> comments;
}
