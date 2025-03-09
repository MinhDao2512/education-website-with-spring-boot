package com.toilamdev.stepbystep.validation.annotation;

import com.toilamdev.stepbystep.validation.validator.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneNumber {
    String message() default "Phone number already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
