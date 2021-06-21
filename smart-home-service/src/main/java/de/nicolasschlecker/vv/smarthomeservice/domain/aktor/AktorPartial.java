package de.nicolasschlecker.vv.smarthomeservice.domain.aktor;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AktorPartial {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String location;

    @NotNull
    private String serviceUrl;
}
