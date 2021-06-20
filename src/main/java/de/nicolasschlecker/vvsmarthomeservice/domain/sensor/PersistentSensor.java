package de.nicolasschlecker.vvsmarthomeservice.domain.sensor;

import de.nicolasschlecker.vvsmarthomeservice.domain.rule.PersistentRule;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class PersistentSensor {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String location;

    @OneToOne()
    @JoinColumn(name = "sensor_data_id", referencedColumnName = "id")
    private PersistentSensorData sensorData;

    @OneToMany(mappedBy = "sensor")
    private List<PersistentRule> rules;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private Timestamp deletedAt;
}
