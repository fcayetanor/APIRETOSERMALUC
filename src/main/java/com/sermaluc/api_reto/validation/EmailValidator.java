package com.sermaluc.api_reto.validation;

import com.sermaluc.api_reto.configuration.ValidationConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidator implements ConstraintValidator<ValidateEmail, String> {

    @Autowired
    private ValidationConfig appProperties;

    @Override
    public void initialize(ValidateEmail constraint) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }
        return email.matches(appProperties.getEmail());
    }
}
