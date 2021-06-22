package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequestDto {
    @NotNull
    @JsonProperty("SensorId")
    private Long id;

    @NotNull
    @JsonProperty("SensorName")
    private String name;

    @NotNull
    @JsonProperty("Location")
    private String location;
}