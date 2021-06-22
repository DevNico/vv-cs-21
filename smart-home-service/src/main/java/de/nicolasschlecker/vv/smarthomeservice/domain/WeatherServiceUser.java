package de.nicolasschlecker.vv.smarthomeservice.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherServiceUser {
    @JsonValue
    private final String username;
    @JsonValue
    private final String password;
}
