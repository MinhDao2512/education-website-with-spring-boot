package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.UserLoginDTO;
import com.toilamdev.stepbystep.dto.request.UserRegisterDTO;
import com.toilamdev.stepbystep.entity.User;

import java.util.Optional;

public interface IUserService {
    boolean checkUserExistsByEmail(String email);

    boolean checkUserExistsByPhoneNumber(String phoneNumber);

    /**
     * Lưu một người dùng mới vào hệ thống.
     *
     * @param userRegisterDTO {@link UserRegisterDTO} Dữ liệu đăng ký người dùng.
     * @return {@link User} Người dùng đã được lưu.
     * @throws RuntimeException Nếu không tìm thấy Role "USER" hoặc nếu tạo người dùng thất bại.
     */
    User saveUser(UserRegisterDTO userRegisterDTO);

    /**
     * Tìm kiếm người dùng theo Email
     *
     * @param email {@link String} Địa chỉ email của người dùng.
     * @return {@link User} Thông tin người dùng được tìm thấy.
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException Nếu không tìm thấy User.
     */
    User fetchUserByEmail(String email);

    /**
     * Xác thực người dùng và trả về Access Token.
     *
     * @param userLoginDTO {@link UserLoginDTO} Dữ liệu đăng nhập người dùng.
     * @return {@link String} Trả về Access Token (JWT).
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException Nếu không tìm thấy người dùng với Email được cung cấp.
     * @throws org.springframework.security.authentication.BadCredentialsException Nếu mật khẩu không khớp với mật khẩu đã được mã hóa trong hệ thống.
     * @throws com.toilamdev.stepbystep.exception.GlobalException.JwtGenerationException Không thể tạo Access Token.
     */
    String login(UserLoginDTO userLoginDTO);
}
