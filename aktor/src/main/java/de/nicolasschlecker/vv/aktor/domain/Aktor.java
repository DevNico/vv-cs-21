package de.nicolasschlecker.vv.aktor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Aktor {
    @JsonProperty("AktorId")
    private Long id;

    @JsonProperty("AktorName")
    private String name;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("ServiceURL")
    private String serviceUrl;
}
