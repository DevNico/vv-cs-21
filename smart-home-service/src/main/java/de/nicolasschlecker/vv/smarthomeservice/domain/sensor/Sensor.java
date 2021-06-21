package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sensor {
    @JsonProperty("SensorId")
    private Long id;

    @JsonProperty("SensorName")
    private String name;

    @JsonProperty("Location")
    private String location;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
