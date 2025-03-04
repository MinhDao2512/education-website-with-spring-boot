package com.toilamdev.stepbystep.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDTO {
    @NotBlank(message = "First name must not be null or empty.")
    private String firstName;

    @NotBlank(message = "Last name must not be null or empty.")
    private String lastName;

    @NotBlank(message = "Email name must not be null or empty.")
    private String email;

    @NotBlank(message = "Password name must not be null or empty.")
    private String password;

    @NotBlank(message = "Phone number must not be null or empty.")
    private String phoneNumber;
}
