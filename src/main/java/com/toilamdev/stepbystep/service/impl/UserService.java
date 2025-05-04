package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.UserLoginRequestDTO;
import com.toilamdev.stepbystep.dto.request.UserRegisterRequestDTO;
import com.toilamdev.stepbystep.entity.Role;
import com.toilamdev.stepbystep.entity.User;
import com.toilamdev.stepbystep.entity.UserRole;
import com.toilamdev.stepbystep.enums.RoleName;
import com.toilamdev.stepbystep.exception.GlobalException;
import com.toilamdev.stepbystep.repository.UserRepository;
import com.toilamdev.stepbystep.repository.UserRoleRepository;
import com.toilamdev.stepbystep.service.IUserService;
import com.toilamdev.stepbystep.utils.FormatUtils;
import com.toilamdev.stepbystep.utils.JWTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTokenUtils jwTokenUtils;

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
    public Integer saveUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        log.info("Bắt đầu tạo user với email: {}", userRegisterRequestDTO.getEmail());
        try {
            Role role = this.roleService.getRoleByName(RoleName.USER);
            User user = User.builder()
                    .firstName(FormatUtils.formatName(userRegisterRequestDTO.getFirstName()))
                    .lastName(FormatUtils.formatName(userRegisterRequestDTO.getLastName()))
                    .email(userRegisterRequestDTO.getEmail())
                    .password(this.passwordEncoder.encode(userRegisterRequestDTO.getPassword()))
                    .phoneNumber(userRegisterRequestDTO.getPhoneNumber())
                    .isInstructor(false)
                    .build();

            user = userRepository.save(user);

            UserRole userRole = UserRole.builder()
                    .role(role)
                    .user(user)
                    .build();

            this.userRoleRepository.save(userRole);

            log.info("User {} được lưu thành công với ID: {}", user.getEmail(), user.getId());
            log.info("UserRole cho user {} được lưu thành công.", user.getEmail());

            return user.getId();
        } catch (GlobalException.RoleNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Không tìm thấy Role phù hợp");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Tạo mới User thất bại");
        }
    }

    @Override
    public User fetchUserByEmail(String email) {
        log.info("Bắt đầu tìm User với email: {}", email);
        return this.userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Không tìm thấy User phù hợp với email: " + email));
    }

    @Override
    public String login(UserLoginRequestDTO userLoginRequestDTO) {
        log.info("Bắt đầu xác thực tài khoản: {}", userLoginRequestDTO.getEmail());
        User user = this.userRepository.findUserByEmail(userLoginRequestDTO.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Email hoặc Mật khẩu không chính xác")
        );

        if (!passwordEncoder.matches(userLoginRequestDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email hoăt Mật khẩu không chính xác");
        }

        if (user.isDeleted()) {
            throw new DisabledException("Tài khoản đã tạm tời bị khóa. Vui lòng liên hệ ADMIN");
        }

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getEmail(),
                        userLoginRequestDTO.getPassword(),
                        user.getAuthorities()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Xác thực tài khoản thành công");
        return this.jwTokenUtils.generateToken(User.builder()
                .email(user.getEmail())
                .userRoles(user.getUserRoles())
                .isInstructor(user.getIsInstructor())
                .build());
    }

    @Override
    public Integer updateUserIsInstructor() {
        log.info("Bắt đầu cập nhật user trở thành instructor");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User user = this.userRepository.findUserByEmail(authentication.getName()).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("Không tìm thấy User phù hợp với email: %s",
                            authentication.getName()))
            );

            user.setIsInstructor(true);

            user = this.userRepository.save(user);
            return user.getId();
        } catch (Exception e) {
            log.error("Cập nhật user không thành công: {}", e.getMessage(), e);
            throw new RuntimeException();
        }
    }
}
