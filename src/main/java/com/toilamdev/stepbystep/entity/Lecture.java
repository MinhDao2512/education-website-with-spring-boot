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

    @OneToOne(mappedBy = "lecture",fetch = FetchType.EAGER)
    private Video video;

    private String document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private Set<Comment> comments;
}
