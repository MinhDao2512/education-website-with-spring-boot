package com.toilamdev.stepbystep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Section extends BaseEntity {
    @Column(nullable = false)
    private String title;

    private float totalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private Set<Lecture> lectures;
}
