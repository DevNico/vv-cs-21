package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorData {
    @JsonValue()
    @JsonProperty("SensorId")
    private Long sensorId;

    @JsonValue()
    @JsonProperty("CurrentValue")
    private int currentValue;

    @JsonValue()
    @JsonProperty("Timestamp")
    private LocalDateTime timestamp;

    @JsonValue()
    @JsonProperty("TemperatureUnit")
    private TemperatureUnit temperatureUnit;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
