package de.nicolasschlecker.vv.smarthomeservice.domain.sensor;

import de.nicolasschlecker.vv.smarthomeservice.domain.rule.PersistentRule;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "sensor")
public class PersistentSensor {
    @Id
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
