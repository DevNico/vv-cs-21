package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class SensorDataPartial {
    @Min(0)
    @Max(30)
    @NotNull
    @JsonValue()
    @JsonProperty("CurrentValue")
    private int currentValue;

    @NotNull
    @JsonValue()
    @JsonProperty("Timestamp")
    private LocalDateTime timestamp;

    @NotNull
    @JsonValue()
    @JsonProperty("TemperatureUnit")
    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;
}
