package de.nicolasschlecker.vvsmarthomeservice.domain.sensor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorData {
    private Long sensorId;
    private int currentValue;
    private LocalDateTime timestamp;
    private TemperatureUnit temperatureUnit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
