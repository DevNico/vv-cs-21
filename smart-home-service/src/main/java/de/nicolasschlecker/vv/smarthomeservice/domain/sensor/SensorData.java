package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorData {
    @JsonProperty("SensorId")
    private Long sensorId;
    private int currentValue;
    private LocalDateTime timestamp;
    private TemperatureUnit temperatureUnit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
