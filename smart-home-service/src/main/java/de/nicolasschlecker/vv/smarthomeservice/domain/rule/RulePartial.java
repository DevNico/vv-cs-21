package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RulePartial {
    @NotBlank
    @JsonValue()
    @JsonProperty("RuleName")
    private String name;

    @Min(1)
    @Max(29)
    @NotNull
    @JsonValue()
    @JsonProperty("Treshhold")
    private int threshold;

    @NotNull
    @JsonValue()
    @JsonProperty("SensorId")
    private Long sensorId;

    @NotNull
    @JsonValue()
    @JsonProperty("AktorId")
    private Long aktorId;
}
