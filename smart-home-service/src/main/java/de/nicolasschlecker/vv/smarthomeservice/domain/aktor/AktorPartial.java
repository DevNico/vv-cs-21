package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AktorPartial {
    @NotNull
    @JsonValue()
    @JsonProperty("AktorName")
    private String name;

    @NotNull
    @JsonValue()
    @JsonProperty("Location")
    private String location;

    @NotNull
    @JsonValue()
    @JsonProperty("ServiceURL")
    private String serviceUrl;
}
