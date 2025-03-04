package com.toilamdev.stepbystep.entity;

import com.toilamdev.stepbystep.enums.PermissionName;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "permissions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Permission extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionName name;

    private String description;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    private Set<RolePermission> rolePermissions;
}
