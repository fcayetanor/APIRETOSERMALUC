package com.sermaluc.api_reto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidatePassword {

    public String message() default "Password es inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
