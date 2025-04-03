package com.toilamdev.stepbystep.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {
    @NotBlank(message = "Bạn chưa nhập thông tin này")
    private String email;

    @NotBlank(message = "Bạn chưa nhập thông tin này")
    private String password;
}
