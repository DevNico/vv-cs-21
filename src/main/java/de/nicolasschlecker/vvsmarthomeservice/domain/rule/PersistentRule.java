package de.nicolasschlecker.vvsmarthomeservice.domain.rule;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.PersistentAktor;
import de.nicolasschlecker.vvsmarthomeservice.domain.sensor.PersistentSensor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;

@Entity
@Data
@IdClass(PersistentRuleId.class)
public class PersistentRule {
    @Id
    @GeneratedValue
    private Long id;

    @Id
    private String name;

    @Min(1)
    @Max(29)
    private int threshold;

    @ManyToOne()
    @JoinColumn(name="sensor_id")
    private PersistentSensor sensor;

    @ManyToOne()
    @JoinColumn(name="aktor_id")
    private PersistentAktor aktor;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
