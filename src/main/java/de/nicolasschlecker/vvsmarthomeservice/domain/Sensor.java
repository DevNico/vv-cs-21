package de.nicolasschlecker.vvsmarthomeservice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Sensor {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String location;

    @OneToOne(cascade = CascadeType.REMOVE)
    private SensorData sensorData;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Rule> rules;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
