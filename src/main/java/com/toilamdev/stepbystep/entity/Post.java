package com.toilamdev.stepbystep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {
    private String title;

    @OneToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;

    private String content;
    private Integer totalLikes;
    private Integer totalDislikes;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contributor_id")
    private User contributor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}
