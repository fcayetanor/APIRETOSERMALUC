package com.sermaluc.api_reto.validation;

import com.sermaluc.api_reto.configuration.ValidationConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordValidator implements ConstraintValidator<ValidatePassword, String> {

    @Autowired
    private ValidationConfig appProperties;

    @Override
    public void initialize(ValidatePassword constraint) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return password.matches(appProperties.getPassword());
    }
}
