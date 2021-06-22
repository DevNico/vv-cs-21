package de.nicolasschlecker.vv.smarthomeservice.domain.sensordata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class SensorDataDto {
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
