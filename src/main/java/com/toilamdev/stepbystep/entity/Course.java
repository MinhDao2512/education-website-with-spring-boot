package com.toilamdev.stepbystep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course extends BaseEntity {
    private String title;
    private String thumbnail;
    private String shortDescription;
    private String description;
    private String requirements;
    private Double price;
    private Float totalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Section> sections;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Review> reviews;

    @OneToOne(mappedBy = "course", fetch = FetchType.LAZY)
    private CartDetail cartDetail;

    @OneToOne(mappedBy = "course", fetch = FetchType.LAZY)
    private OrderDetail orderDetail;
}
