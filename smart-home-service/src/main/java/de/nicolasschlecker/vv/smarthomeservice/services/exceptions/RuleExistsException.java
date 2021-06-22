package de.nicolasschlecker.vv.smarthomeservice.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
public class RuleExistsException extends RuntimeException {
    public RuleExistsException() {
        super("Rule already exists");
    }
}
