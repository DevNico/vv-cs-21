package de.nicolasschlecker.vv.smarthomeservice.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RuleNotFoundException extends RuntimeException {
    public RuleNotFoundException() {
        super("No Rule with given id found");
    }
}
