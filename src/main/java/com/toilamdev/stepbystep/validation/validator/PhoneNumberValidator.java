package com.toilamdev.stepbystep.validation.validator;

import com.toilamdev.stepbystep.service.impl.UserService;
import com.toilamdev.stepbystep.validation.annotation.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !this.userService.checkUserExistsByPhoneNumber(value);
    }
}
