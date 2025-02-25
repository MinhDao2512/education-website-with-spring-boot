package com.toilamdev.stepbystep.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginDTO {
    @NotBlank(message = "Email must not empty.")
    private String email;

    @NotBlank(message = "Password must not empty")
    private String password;
}
