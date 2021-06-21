package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SensorPartial {
    @NotNull
    @JsonValue()
    @JsonProperty("SensorId")
    private Long id;

    @NotNull
    @JsonValue()
    @JsonProperty("SensorName")
    private String name;

    @NotNull
    @JsonValue()
    @JsonProperty("Location")
    private String location;
}