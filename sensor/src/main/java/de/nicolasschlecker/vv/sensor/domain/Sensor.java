package de.nicolasschlecker.vv.sensor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sensor {
    @JsonProperty("SensorId")
    private Long id;

    @JsonProperty("SensorName")
    private String name;

    @JsonProperty("Location")
    private String location;
}