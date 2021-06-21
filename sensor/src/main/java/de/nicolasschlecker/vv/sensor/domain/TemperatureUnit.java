package de.nicolasschlecker.vv.sensor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TemperatureUnit {
    @JsonProperty("Celsius")
    CELSIUS,
    @JsonProperty("Kelvin")
    KELVIN,
    @JsonProperty("Fahrenheit")
    FAHRENHEIT
}
