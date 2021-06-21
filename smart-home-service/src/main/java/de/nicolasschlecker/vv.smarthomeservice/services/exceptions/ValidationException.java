package de.nicolasschlecker.vvsmarthomeservice.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.stream.Collectors;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    public ValidationException(List<ConstraintViolation<?>> violations) {
        super(violations.stream().map(v -> String.format("\"%s\" %s", v.getPropertyPath(), v.getMessage())).collect(Collectors.joining(", ")));
    }
}
