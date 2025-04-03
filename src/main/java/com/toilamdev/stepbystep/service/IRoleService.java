package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.enums.RoleName;

public interface IRoleService {
    void createNewRoles();

    Role getRoleByName(RoleName roleName);
}
