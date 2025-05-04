package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.UserLoginRequestDTO;
import com.toilamdev.stepbystep.dto.request.UserRegisterRequestDTO;
import com.toilamdev.stepbystep.entity.User;

public interface IUserService {
    boolean checkUserExistsByEmail(String email);

    boolean checkUserExistsByPhoneNumber(String phoneNumber);

    /**
     * Lưu một người dùng mới vào hệ thống.
     *
     * @param userRegisterRequestDTO {@link UserRegisterRequestDTO} Dữ liệu đăng ký người dùng.
     * @return {@link Integer} Id của người dùng mới.
     * @throws RuntimeException Nếu không tìm thấy Role "USER" hoặc nếu tạo người dùng thất bại.
     */
    Integer saveUser(UserRegisterRequestDTO userRegisterRequestDTO);

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
     * @param userLoginRequestDTO {@link UserLoginRequestDTO} Dữ liệu đăng nhập người dùng.
     * @return {@link String} Trả về Access Token (JWT).
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException Nếu không tìm thấy người dùng với Email được cung cấp.
     * @throws org.springframework.security.authentication.BadCredentialsException Nếu mật khẩu không khớp với mật khẩu đã được mã hóa trong hệ thống.
     * @throws com.toilamdev.stepbystep.exception.GlobalException.JwtGenerationException Không thể tạo Access Token.
     */
    String login(UserLoginRequestDTO userLoginRequestDTO);

    /**
     * Cập nhật User trở thành Instructor
     *
     * @return {@link Integer} Trả về Id của người dùng
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException Nếu không tìm thấy người dùng với Email
     */
    Integer updateUserIsInstructor();
}
