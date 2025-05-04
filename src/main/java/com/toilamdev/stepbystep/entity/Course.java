package com.toilamdev.stepbystep.entity;

import com.toilamdev.stepbystep.enums.CourseLabel;
import com.toilamdev.stepbystep.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;

import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends BaseEntity {
    private String title;

    @OneToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;

    private String description;
    private String requirements;
    private Double price;
    private Long totalDuration;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Enumerated(EnumType.STRING)
    private CourseLabel label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Section> sections;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Review> reviews;

    @OneToOne(mappedBy = "course", fetch = FetchType.EAGER)
    private CartDetail cartDetail;

    @OneToOne(mappedBy = "course", fetch = FetchType.EAGER)
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "course_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments;
}
