package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Aktor {
    @JsonProperty("AktorId")
    private Long id;

    @JsonProperty("AktorName")
    private String name;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("ServiceURL")
    private String serviceUrl;

    private ShutterStatus currentState;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
