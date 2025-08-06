package de.hsrm.mi.web.projekt.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = GeeigneteLosungValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GeeigneteLosung {
    String message() default "{benutzer.fehler.geeignetelosung}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

