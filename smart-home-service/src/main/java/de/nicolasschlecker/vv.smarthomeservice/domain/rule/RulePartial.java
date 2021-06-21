package de.nicolasschlecker.vvsmarthomeservice.domain.rule;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RulePartial {
    @NotNull
    private String name;

    @Min(1)
    @Max(29)
    @NotNull
    private int threshold;

    @NotNull
    private Long sensorId;

    @NotNull
    private Long aktorId;
}
