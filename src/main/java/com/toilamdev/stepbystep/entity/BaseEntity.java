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

    private boolean isDeleted;

    @Version
    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant deletedAt;

    @PrePersist
    public void create() {
        this.createdBy = AuthenticationUtils.getPrincipal().orElse("PRODUCT OWNER");
        this.createdAt = Instant.now();
        this.isDeleted = false;
    }

    @PreUpdate
    public void update() {
        this.updatedBy = AuthenticationUtils.getPrincipal().orElse("PRODUCT OWNER");
        this.updatedAt = Instant.now();
    }
}
