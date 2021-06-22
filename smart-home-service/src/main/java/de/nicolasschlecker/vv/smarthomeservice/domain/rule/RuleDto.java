package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class RuleDto {
    @JsonProperty("RuleId")
    private Long id;

    @JsonProperty("RuleName")
    private String name;

    @JsonProperty("Treshhold")
    private int threshold;

    @JsonProperty("SensorId")
    private Long sensorId;

    @JsonProperty("AktorId")
    private Long aktorId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
