package com.toilamdev.stepbystep.configuration;

import com.toilamdev.stepbystep.entity.User;
import com.toilamdev.stepbystep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Email hoặc Mật khẩu không chính xác")
        );

        if (!user.getIsActive()) {
            throw new DisabledException("Tài khoản chưa kích hoạt");
        }

        if (user.getIsDeleted()) {
            throw new DisabledException("Tài khoản đã bị vô hóa. Hãy liên hệ với ADMIN");
        }

        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .userRoles(user.getUserRoles())
                .build();
    }
}
