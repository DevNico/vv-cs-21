package de.nicolasschlecker.vv.smarthomeservice.domain.sensordata;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class SensorDataRequestDto {
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
