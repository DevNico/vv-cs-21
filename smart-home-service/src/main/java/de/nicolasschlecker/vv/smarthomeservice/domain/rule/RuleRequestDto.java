package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class RuleRequestDto {
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
