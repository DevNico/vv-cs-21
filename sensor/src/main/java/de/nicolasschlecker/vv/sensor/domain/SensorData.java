package de.nicolasschlecker.vv.sensor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SensorData {
    @JsonProperty("CurrentValue")
    private int currentValue;

    @JsonProperty("Timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("TemperatureUnit")
    private TemperatureUnit temperatureUnit;
}
