package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AktorPartial {
    @NotNull
    @JsonProperty("AktorId")
    private Long id;

    @NotNull
    @JsonProperty("AktorName")
    private String name;

    @NotNull
    @JsonProperty("Location")
    private String location;

    @NotNull
    @JsonProperty("ServiceURL")
    private String serviceUrl;
}