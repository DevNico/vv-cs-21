package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("CurrentValue")
    @JsonAlias("Temperature")
    private int currentValue;

    @JsonProperty("Timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    @NotNull
    @JsonProperty("TemperatureUnit")
    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;
}
