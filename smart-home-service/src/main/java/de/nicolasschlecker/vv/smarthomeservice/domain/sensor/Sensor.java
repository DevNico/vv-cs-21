package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sensor {

    private Long id;
    private String name;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
