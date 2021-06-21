package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TemperatureUnit {
    @JsonProperty("Celsius")
    CELSIUS,
    @JsonProperty("Kelvin")
    KELVIN,
    @JsonProperty("Fahrenheit")
    FAHRENHEIT
}
