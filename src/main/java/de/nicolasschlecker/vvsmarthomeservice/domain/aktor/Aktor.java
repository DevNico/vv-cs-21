package de.nicolasschlecker.vvsmarthomeservice.domain.aktor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Aktor {
    private Long id;
    private String name;
    private String location;
    private String serviceUrl;
    private String currentState;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
