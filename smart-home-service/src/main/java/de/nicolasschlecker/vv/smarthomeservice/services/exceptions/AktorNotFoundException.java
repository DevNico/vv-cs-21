package de.nicolasschlecker.vv.smarthomeservice.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AktorNotFoundException extends RuntimeException {
    public AktorNotFoundException() {
        super("No Aktor with given id found");
    }
}
