package de.nicolasschlecker.vv.smarthomeservice.domain.sensordata;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TemperatureUnit {
    @JsonProperty("Celsius")
    CELSIUS,
    @JsonProperty("Kelvin")
    KELVIN,
    @JsonProperty("Fahrenheit")
    FAHRENHEIT
}
