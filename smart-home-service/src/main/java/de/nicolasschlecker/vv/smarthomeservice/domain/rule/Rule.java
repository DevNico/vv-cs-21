package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Rule {
    @JsonValue()
    @JsonProperty("RuleId")
    private Long id;

    @JsonValue()
    @JsonProperty("RuleName")
    private String name;

    @JsonValue()
    @JsonProperty("Treshhold")
    private int threshold;

    @JsonValue()
    @JsonProperty("SensorId")
    private Long sensorId;

    @JsonValue()
    @JsonProperty("AktorId")
    private Long aktorId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
