package com.toilamdev.stepbystep.service.impl;

import com.toilamdev.stepbystep.dto.request.UserLoginDTO;
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
import com.toilamdev.stepbystep.utils.JWTokenUtils;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public User fetchUserByEmail(String email) {
        log.info("Bắt đầu tìm User với email: {}", email);
        return this.userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Không tìm thấy User phù hợp với email: " + email));
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        log.info("Bắt đầu xác thực tài khoản: {}", userLoginDTO.getEmail());
        User user = this.userRepository.findUserByEmail(userLoginDTO.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Email hoặc Mật khẩu không hợp lệ")
        );

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email hoăt Mật khẩu không chính xác");
        }

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(),
                        userLoginDTO.getPassword(),
                        user.getAuthorities()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Xác thực tài khoản thành công");
        return this.jwTokenUtils.generateToken(user);
    }
}
