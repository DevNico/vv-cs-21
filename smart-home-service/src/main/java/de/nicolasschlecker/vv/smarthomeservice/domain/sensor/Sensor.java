package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sensor {
    @JsonValue()
    @JsonProperty("SensorId")
    private Long id;

    @JsonValue()
    @JsonProperty("SensorName")
    private String name;

    @JsonValue()
    @JsonProperty("Location")
    private String location;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
