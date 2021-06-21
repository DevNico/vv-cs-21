package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SensorPartial {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String location;
}
