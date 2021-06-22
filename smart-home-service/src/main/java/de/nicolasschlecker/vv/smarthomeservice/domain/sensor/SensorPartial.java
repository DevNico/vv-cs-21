package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SensorPartial {
    @NotNull
    @JsonProperty("SensorId")
    private Long id;

    @NotNull
    @JsonProperty("SensorName")
    private String name;

    @NotNull
    @JsonProperty("Location")
    private String location;
}