package de.hsrm.mi.web.projekt.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GeeigneteLosungValidator implements ConstraintValidator<GeeigneteLosung, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        String normalized = value.toLowerCase();

        return !(normalized.contains("42") || normalized.contains("zweiundvierzig"));
    }
}

