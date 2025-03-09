package com.toilamdev.stepbystep.repository;

import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(RoleName roleName);
}
