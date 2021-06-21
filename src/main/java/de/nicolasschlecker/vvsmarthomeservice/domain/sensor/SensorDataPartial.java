package de.nicolasschlecker.vvsmarthomeservice.domain.sensor;

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
    private int currentValue;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private TemperatureUnit temperatureUnit;
}
