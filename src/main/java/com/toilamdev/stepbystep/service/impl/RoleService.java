package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.enums.RoleName;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.RoleRepository;
import com.toilamdev.stepbystep.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public void createNewRoles() {
        log.info("Bắt đầu thêm mới các Role");
        try{
            for(RoleName roleName : RoleName.values()) {
                Role role = Role.builder()
                        .name(roleName)
                        .build();

                switch (roleName){
                    case ADMIN -> role.setDescription("Quản trị viên, quản lý các hoạt động chung của hệ thống.");
                    case USER -> role.setDescription("Người dùng thông thường");
                    case MANAGER -> role.setDescription("Điều phối viên, chịu trách nhiệm kiểm duyệt nội dung, giám sát hoạt động của người dùng");
                    default -> role.setDescription("Quản trị hệ thống cấp cao.");
                }

                this.roleRepository.save(role);
            }
            log.info("Thêm mới Roles thành công");
        }catch (Exception e){
            log.error("Có lỗi xảy ra khi tạo mới Roles: {}", e.getMessage(), e);
            throw new GlobalException.NotCreateRolesException("Don't Create New Roles");
        }
    }

    @Override
    public Role getRoleByName(RoleName roleName) {
        log.info("Bắt đầu tìm kiếm Role theo tên: {}", roleName);
        return this.roleRepository.findRoleByName(roleName).orElseThrow(() ->
                new GlobalException.RoleNotFoundException("Role not found"));
    }
}
