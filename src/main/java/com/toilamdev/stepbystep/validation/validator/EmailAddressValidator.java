package com.toilamdev.stepbystep.validation.validator;

import com.toilamdev.stepbystep.service.impl.UserService;
import com.toilamdev.stepbystep.validation.annotation.EmailAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailAddressValidator implements ConstraintValidator<EmailAddress, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regexp = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|yahoo\\.com|outlook\\.com|icloud\\.com)$";
        return (value.matches(regexp) && !this.userService.checkUserExistsByEmail(value));
    }
}
