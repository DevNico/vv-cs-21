package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Rule {
    private Long id;
    private String name;
    private int threshold;
    private Long sensorId;
    private Long aktorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
