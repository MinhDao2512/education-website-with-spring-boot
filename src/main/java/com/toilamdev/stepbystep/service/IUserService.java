package com.toilamdev.stepbystep.service;

import com.toilamdev.stepbystep.dto.request.UserRegisterDTO;
import com.toilamdev.stepbystep.entity.User;

public interface IUserService {
    boolean checkUserExistsByEmail(String email);

    boolean checkUserExistsByPhoneNumber(String phoneNumber);

    User saveUser(UserRegisterDTO userRegisterDTO);
}
