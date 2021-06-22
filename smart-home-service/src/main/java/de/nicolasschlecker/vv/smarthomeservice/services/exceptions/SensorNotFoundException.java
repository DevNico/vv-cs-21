package de.nicolasschlecker.vv.smarthomeservice.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException() {
        super("No Sensor with given id found");
    }
}
