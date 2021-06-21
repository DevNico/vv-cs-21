package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorData {
    @JsonProperty("SensorId")
    private Long sensorId;

    @JsonProperty("CurrentValue")
    private int currentValue;

    @JsonProperty("Timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("TemperatureUnit")
    private TemperatureUnit temperatureUnit;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
