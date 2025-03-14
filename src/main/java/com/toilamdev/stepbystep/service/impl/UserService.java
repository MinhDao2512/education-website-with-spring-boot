package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.UserRegisterDTO;
import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.entity.User;
import com.toilamdev.stepbystep.entity.UserRole;
import com.toilamdev.stepbystep.enums.RoleName;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.UserRepository;
import com.toilamdev.stepbystep.repository.UserRoleRepository;
import com.toilamdev.stepbystep.service.IUserService;
import com.toilamdev.stepbystep.utils.FormatUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRoleRepository userRoleRepository;

    @Override
    public boolean checkUserExistsByEmail(String email) {
        return this.userRepository.existsUserByEmail(email);
    }

    @Override
    public boolean checkUserExistsByPhoneNumber(String phoneNumber) {
        return this.userRepository.existsUserByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public User saveUser(UserRegisterDTO userRegisterDTO) {
        log.info("Bắt đầu tạo user với email: {}", userRegisterDTO.getEmail());
        try {
            Role role = this.roleService.getRoleByName(RoleName.USER);
            User user = User.builder()
                    .firstName(FormatUtils.formatName(userRegisterDTO.getFirstName()))
                    .lastName(FormatUtils.formatName(userRegisterDTO.getLastName()))
                    .email(userRegisterDTO.getEmail())
                    .password(this.passwordEncoder.encode(userRegisterDTO.getPassword()))
                    .phoneNumber(userRegisterDTO.getPhoneNumber())
                    .build();

            User savedUser = userRepository.save(user);

            UserRole userRole = UserRole.builder()
                    .role(role)
                    .user(user)
                    .build();

            this.userRoleRepository.save(userRole);

            log.info("User {} được lưu thành công với ID: {}", savedUser.getEmail(), savedUser.getId());
            log.info("UserRole cho user {} được lưu thành công.", savedUser.getEmail());

            return savedUser;
        } catch (GlobalException.RoleNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Không tìm thấy Role phù hợp");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Tạo mới User thất bại");
        }
    }
}
