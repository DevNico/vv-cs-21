package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Aktor {
    @JsonValue()
    @JsonProperty("AktorId")
    private Long id;

    @JsonValue()
    @JsonProperty("AktorName")
    private String name;

    @JsonValue()
    @JsonProperty("Location")
    private String location;

    @JsonValue()
    @JsonProperty("ServiceURL")
    private String serviceUrl;

    private ShutterStatus currentState;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
