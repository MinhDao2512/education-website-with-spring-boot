package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.enums.RoleName;
import com.toilamdev.stepbystep.repository.RoleRepository;
import com.toilamdev.stepbystep.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role getRoleByName(RoleName roleName) {
        return this.roleRepository.findRoleByName(roleName);
    }
}
