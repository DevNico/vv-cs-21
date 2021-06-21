package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Aktor {
    private Long id;
    private String name;
    private String location;
    private String serviceUrl;
    private ShutterStatus currentState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
