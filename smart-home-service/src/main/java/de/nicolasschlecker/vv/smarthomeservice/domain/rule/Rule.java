package de.nicolasschlecker.vv.smarthomeservice.domain.rule;

import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.Aktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.sensor.Sensor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private int threshold;

    @ManyToOne()
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @ManyToOne()
    @JoinColumn(name = "aktor_id")
    private Aktor aktor;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
