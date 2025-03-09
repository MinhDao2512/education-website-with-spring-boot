package com.toilamdev.stepbystep.validation.annotation;

import com.toilamdev.stepbystep.validation.validator.EmailAddressValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = EmailAddressValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmailAddress {
    String message() default "Email is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
