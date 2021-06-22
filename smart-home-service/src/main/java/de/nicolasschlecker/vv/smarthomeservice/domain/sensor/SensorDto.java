package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class SensorDto {
    @JsonProperty("SensorId")
    private Long id;

    @JsonProperty("SensorName")
    private String name;

    @JsonProperty("Location")
    private String location;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
