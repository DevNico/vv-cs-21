package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RulePartial {
    @NotNull
    @JsonProperty("RuleName")
    private String name;

    @Min(1)
    @Max(29)
    @NotNull
    @JsonProperty("Treshhold")
    private int threshold;

    @NotNull
    @JsonProperty("SensorId")
    private Long sensorId;

    @NotNull
    @JsonProperty("AktorId")
    private Long aktorId;
}
