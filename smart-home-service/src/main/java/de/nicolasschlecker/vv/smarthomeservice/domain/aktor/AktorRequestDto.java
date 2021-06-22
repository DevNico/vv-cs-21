package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AktorRequestDto {
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
