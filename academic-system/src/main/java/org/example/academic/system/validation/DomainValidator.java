package org.example.academic.system.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.academic.system.exception.AcademicSystemException;

import java.util.Set;
import java.util.stream.Collectors;

public class DomainValidator {

    private static final Validator validator;

    static {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations =
                validator.validate(object);
        if (!violations.isEmpty()) {
            String messages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new AcademicSystemException(
                    "Validation failed: " + messages);
        }
    }
}