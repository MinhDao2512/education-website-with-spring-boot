package com.toilamdev.stepbystep.repository;

import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName(RoleName roleName);
}
