package com.toilamdev.stepbystep.entity;

import com.toilamdev.stepbystep.utils.AuthenticationUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private boolean isActive;

    @Version
    @Column(nullable = false)
    private int version;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private Instant createAt;
    private String updatedBy;
    private Instant updatedAt;

    @PrePersist
    public void create() {
        this.createdBy = AuthenticationUtils.getPrincipal().orElse("Anonymous");
        this.createAt = Instant.now();
        this.isActive = true;
    }

    @PreUpdate
    public void update() {
        this.updatedBy = AuthenticationUtils.getPrincipal().orElse("Anonymous");
        this.updatedAt = Instant.now();
    }
}
