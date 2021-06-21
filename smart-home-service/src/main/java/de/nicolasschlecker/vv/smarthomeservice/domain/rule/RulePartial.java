package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RulePartial {
    @NotBlank
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
