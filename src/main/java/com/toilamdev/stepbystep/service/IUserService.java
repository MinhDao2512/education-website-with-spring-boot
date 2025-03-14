package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.UserRegisterDTO;
import com.toilamdev.stepbystep.entity.User;

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
}
