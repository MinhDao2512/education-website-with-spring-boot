package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.enums.RoleName;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();

    Role getRoleByName(RoleName roleName);
}
