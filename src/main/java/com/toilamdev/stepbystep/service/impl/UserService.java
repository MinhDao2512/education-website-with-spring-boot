package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.UserRegisterDTO;
import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.entity.User;
import com.toilamdev.stepbystep.entity.UserRole;
import com.toilamdev.stepbystep.enums.RoleName;
import com.toilamdev.stepbystep.repository.UserRepository;
import com.toilamdev.stepbystep.service.IUserService;
import com.toilamdev.stepbystep.utils.FormatUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public boolean checkUserExistsByEmail(String email) {
        return this.userRepository.existsUserByEmail(email);
    }

    @Override
    public boolean checkUserExistsByPhoneNumber(String phoneNumber) {
        return this.userRepository.existsUserByPhoneNumber(phoneNumber);
    }

    @Override
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

            UserRole userRole = UserRole.builder()
                    .role(role)
                    .user(user)
                    .build();

            User savedUser = userRepository.save(user);

            log.info("User {} được lưu thành công với ID: {}", savedUser.getEmail(), savedUser.getId());

            return savedUser;
        } catch (Exception e) {
            log.error("Lỗi khi lưu user với email {}: {}", userRegisterDTO.getEmail(), e.getMessage(), e);
            throw e;
        }
    }
}
